package pt.up.fe.cpm.tiktek.feature.events

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.domain.NetworkErrorUseCase
import pt.up.fe.cpm.tiktek.core.domain.UnknownErrorUseCase
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import javax.inject.Inject

data class EventDialogUiState(
    val loading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
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

        val buyTicketsSuccess = MutableStateFlow(false)

        fun removeTicket() {
            eventDialogUiState =
                eventDialogUiState.copy(
                    ticketAmount = maxOf(eventDialogUiState.ticketAmount - 1, 1),
                )
        }

        fun addTicket() {
            eventDialogUiState =
                eventDialogUiState.copy(
                    ticketAmount = minOf(eventDialogUiState.ticketAmount + 1, 4),
                )
        }

        fun buyTickets() =
            viewModelScope.launch {
                eventDialogUiState = eventDialogUiState.copy(loading = true, errorMessage = null)

                when (eventsRepository.buyTickets(eventId, eventDialogUiState.ticketAmount)) {
                    is NetworkResult.Success -> buyTicketsSuccess.emit(true)
                    is NetworkResult.Failure -> eventDialogUiState = eventDialogUiState.copy(errorMessage = NetworkErrorUseCase())
                    is NetworkResult.Error -> eventDialogUiState = eventDialogUiState.copy(errorMessage = UnknownErrorUseCase())
                }

                eventDialogUiState = eventDialogUiState.copy(loading = false)
            }
    }
