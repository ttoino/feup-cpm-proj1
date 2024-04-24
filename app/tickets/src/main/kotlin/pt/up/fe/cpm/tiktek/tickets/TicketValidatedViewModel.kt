package pt.up.fe.cpm.tiktek.tickets

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.destinations.TicketValidatedRouteDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.core.data.TicketsTerminalRepository
import pt.up.fe.cpm.tiktek.core.model.LoadState
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import javax.inject.Inject

@HiltViewModel
class TicketValidatedViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val ticketsTerminalRepository: TicketsTerminalRepository,
    ) : ViewModel() {
        private val navArgs = TicketValidatedRouteDestination.argsFrom(savedStateHandle)

        var qrCodeResult = navArgs.qrCodeResult
        val returnedTicket = MutableStateFlow<LoadState<TicketWithEvent>>(LoadState.Loading)

        init {
            viewModelScope.launch {
                val request =
                    try {
                        Json.decodeFromString<SendTicketRequest>(qrCodeResult)
                    } catch (e: Exception) {
                        returnedTicket.value = LoadState.Error("Failed to parse QR code")
                        return@launch
                    }
                when (val result = ticketsTerminalRepository.sendTicket(request)) {
                    is NetworkResult.Success -> returnedTicket.value = LoadState.Success(result.value)
                    else -> returnedTicket.value = LoadState.Error("Failed to validate ticket")
                }
            }
        }
    }
