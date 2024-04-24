package pt.up.fe.cpm.tiktek.feature.cafeteria

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest
import javax.inject.Inject

@HiltViewModel
class CartScanViewModel
    @Inject
    constructor(
        private val cartRepository: CartRepository,
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val encoder = QRCodeWriter()

        val cartCode =
            combine(cartRepository.getCart(), userRepository.getUser()) { cart, user ->
                if (user == null) return@combine null

                val json = Json.encodeToString(SendCartRequest(user.id, cart))
                val bitMatrix = encoder.encode(json, BarcodeFormat.QR_CODE, 256, 256)
                Bitmap.createBitmap(256, 256, Bitmap.Config.ARGB_8888).also { bitmap ->
                    for (x in 0 until 256) {
                        for (y in 0 until 256) {
                            bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                        }
                    }
                }.asImageBitmap()
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null,
            )
    }
