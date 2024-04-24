package pt.up.fe.cpm.tiktek.core.data

import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent

interface TicketsTerminalRepository {
    suspend fun sendTicket(request: SendTicketRequest): NetworkResult<TicketWithEvent>
}
