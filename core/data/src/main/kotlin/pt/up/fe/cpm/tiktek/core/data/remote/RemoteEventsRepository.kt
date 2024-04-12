package pt.up.fe.cpm.tiktek.core.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class RemoteEventsRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val userRepository: UserRepository,
    ) : EventsRepository {
        override fun getEvents(): Flow<List<Event>> =
            userRepository.getToken().map {
                when (it) {
                    null -> emptyList()
                    else -> networkDataSource.getEvents(it)
                }
            }
    }
