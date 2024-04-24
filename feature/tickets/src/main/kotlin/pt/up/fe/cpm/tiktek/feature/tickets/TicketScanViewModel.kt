package pt.up.fe.cpm.tiktek.feature.tickets

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import com.ramcosta.composedestinations.generated.tickets.destinations.TicketScanRouteDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import javax.inject.Inject

@HiltViewModel
class TicketScanViewModel
    @Inject
    constructor(
        private val ticketsRepository: TicketsRepository,
        private val savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val navArgs = TicketScanRouteDestination.argsFrom(savedStateHandle)

        private val ticketId = navArgs.ticketId

        private val encoder = QRCodeWriter()

        val ticketCode =
            ticketsRepository.getTicket(ticketId).map { ticket ->
                val json = Json.encodeToString(SendTicketRequest(ticket.id))
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
