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
        val returnedTicket = MutableStateFlow<TicketWithEvent?>(null)

        init {
            sendTicket()
        }

        fun sendTicket() =
            viewModelScope.launch {
                val sendTicketRequest = Json.decodeFromString<SendTicketRequest>(qrCodeResult)
                val ticket = sendTicketRequest.ticket

                var result = ticketsTerminalRepository.sendTicket(ticket)

                when (result) {
                    is NetworkResult.Success -> {
                        returnedTicket.value = result.value
                    }
                    else -> returnedTicket.value = null
                }
            }
    }
