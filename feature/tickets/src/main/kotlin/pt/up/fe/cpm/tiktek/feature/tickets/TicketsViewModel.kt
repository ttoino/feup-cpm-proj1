package pt.up.fe.cpm.tiktek.feature.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel
    @Inject
    constructor(
        private val ticketsRepository: TicketsRepository,
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        val tickets =
            combine(
                ticketsRepository.getTickets(),
                eventsRepository.getEvents(),
            ) { tickets, events ->
                tickets.map {
                        ticket ->
                    TicketWithEvent(
                        ticket.id,
                        events.first { it.id == ticket.eventId },
                        ticket.userEmail,
                        ticket.seat,
                        ticket.purchaseDate,
                    )
                }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
