package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.Ticket

interface TicketDAO {
    suspend fun getAllByUser(userEmail: String): List<Ticket>

    suspend fun getById(id: String): Ticket?

    suspend fun create(ticket: Ticket): Ticket

    suspend fun update(ticket: Ticket): Ticket

    suspend fun delete(id: String): Boolean
}
