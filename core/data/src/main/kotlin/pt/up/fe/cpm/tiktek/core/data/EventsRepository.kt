package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Event

interface EventsRepository {
    fun getEvents(): Flow<List<Event>>
}