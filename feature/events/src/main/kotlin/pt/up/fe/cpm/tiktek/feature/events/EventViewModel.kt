package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject

data class EventDialogUiState(
    val ticketAmount: Int = 1,
)

@HiltViewModel
class EventViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        private val navArgs = EventDestination.argsFrom(savedStateHandle)

        val eventId = navArgs.eventId

        val event: StateFlow<Event?> =
            eventsRepository.getEvent(
                eventId,
            ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

        var eventDialogUiState by mutableStateOf(EventDialogUiState())
            private set

        fun removeTicket() {
            eventDialogUiState =
                eventDialogUiState.copy(
                    ticketAmount = maxOf(eventDialogUiState.ticketAmount - 1, 1),
                )
        }

        fun addTicket() {
            eventDialogUiState =
                eventDialogUiState.copy(
                    ticketAmount = eventDialogUiState.ticketAmount + 1,
                )
        }

        fun buyTickets() =
            viewModelScope.launch {
            }
    }
