package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Event

interface EventsRepository : Syncable {
    fun getEvents(): Flow<List<Event>>

    fun getEvent(id: String): Flow<Event>
}
