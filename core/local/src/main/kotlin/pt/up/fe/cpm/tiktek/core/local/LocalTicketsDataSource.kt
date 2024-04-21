package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Ticket

interface LocalTicketsDataSource {
    fun getTickets(): Flow<List<Ticket>>

    fun getTicket(id: String): Flow<Ticket>

    suspend fun insert(tickets: List<Ticket>)
}
