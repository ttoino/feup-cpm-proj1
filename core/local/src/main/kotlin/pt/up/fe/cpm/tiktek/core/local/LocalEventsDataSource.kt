package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Event

interface LocalEventsDataSource {
    fun getEvents(): Flow<List<Event>>

    fun getEvent(id: String): Flow<Event>

    suspend fun insert(events: List<Event>)
}
