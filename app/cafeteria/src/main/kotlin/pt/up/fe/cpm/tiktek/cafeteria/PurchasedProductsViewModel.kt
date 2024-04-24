package pt.up.fe.cpm.tiktek.cafeteria

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.PurchasedProductsRouteDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.core.data.CafeteriaTerminalRepository
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest
import javax.inject.Inject

@HiltViewModel
class PurchasedProductsViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val cafeteriaTerminalRepository: CafeteriaTerminalRepository,
    ) : ViewModel() {
        private val navArgs = PurchasedProductsRouteDestination.argsFrom(savedStateHandle)

        val qrCodeResult = navArgs.qrCodeResult
        val orderWithModels = MutableStateFlow<OrderWithModels?>(null)

        init {
            sendCart()
        }

        fun sendCart() =
            viewModelScope.launch {
                val sendCartRequest = Json.decodeFromString<SendCartRequest>(qrCodeResult)
                val userId = sendCartRequest.userId
                val items = sendCartRequest.cart
                val result =
                    cafeteriaTerminalRepository.sendCart(userId, items)

                when (result) {
                    is NetworkResult.Success -> {
                        orderWithModels.value = result.value
                    }
                    else -> orderWithModels.value = null
                }
            }
    }
