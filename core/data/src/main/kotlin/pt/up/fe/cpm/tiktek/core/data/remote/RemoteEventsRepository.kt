package pt.up.fe.cpm.tiktek.core.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class RemoteEventsRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
    ) : EventsRepository {
        override fun getEvents(): Flow<List<Event>> =
            flow {
//                networkDataSource.getEvents()
            }
    }
