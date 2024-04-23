package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.Ticket

interface TicketDAO {
    suspend fun getAllByUser(userId: String): List<Ticket>

    suspend fun getById(id: String): Ticket?

    suspend fun getLastSeatByEventId(eventId: String): String?

    suspend fun create(ticket: Ticket): Ticket

    suspend fun createAll(tickets: List<Ticket>): List<Ticket>

    suspend fun update(ticket: Ticket): Ticket

    suspend fun delete(id: String): Boolean
}
