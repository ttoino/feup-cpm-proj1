package pt.up.fe.cpm.tiktek.feature.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.Ticket
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel
    @Inject
    constructor(
        private val ticketsRepository: TicketsRepository,
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch { ticketsRepository.sync() }
            viewModelScope.launch { eventsRepository.sync() }
        }

        val tickets: StateFlow<List<Ticket>> =
            ticketsRepository.getTickets().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )
        val events: StateFlow<List<Event>> =
            eventsRepository.getEvents().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )

        fun getEventImage(eventId: String): String? {
            val event = events.value.firstOrNull { it.id == eventId }
            return event?.imageUrl
        }
    }
