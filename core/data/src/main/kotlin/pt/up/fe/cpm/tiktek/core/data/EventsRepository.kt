package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.NetworkResult

interface EventsRepository {
    fun getEvents(): Flow<List<Event>>

    fun getEvent(id: String): Flow<Event>

    suspend fun buyTickets(
        id: String,
        ticketAmount: Int,
    ): NetworkResult<Unit>
}
