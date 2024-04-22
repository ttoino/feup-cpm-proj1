package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Ticket

interface TicketsRepository : Syncable {
    fun getTickets(): Flow<List<Ticket>>

    fun getTicket(id: String): Flow<Ticket>
}
