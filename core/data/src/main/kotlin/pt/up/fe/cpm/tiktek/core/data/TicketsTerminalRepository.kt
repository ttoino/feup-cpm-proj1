package pt.up.fe.cpm.tiktek.core.data

import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent

interface TicketsTerminalRepository {
    suspend fun sendTicket(ticket: Ticket): NetworkResult<TicketWithEvent>
}
