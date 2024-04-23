package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.work.Syncable
import pt.up.fe.cpm.tiktek.core.local.LocalEventsDataSource
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstEventsRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalEventsDataSource,
        private val userRepository: UserRepository,
    ) : EventsRepository, Syncable {
        override suspend fun sync(): NetworkResult<Unit> {
            val token = userRepository.getToken().first() ?: return NetworkResult.Failure

            val result = networkDataSource.getEvents(token)

            result.getOrNull()?.let {
                localDataSource.insert(it)
            }

            return result.map {}
        }

        override fun getEvents(): Flow<List<Event>> = localDataSource.getEvents()

        override fun getEvent(id: String): Flow<Event> = localDataSource.getEvent(id)

        override suspend fun buyTickets(
            id: String,
            ticketAmount: Int,
        ): NetworkResult<Unit> {
            val token = userRepository.getToken().first() ?: return NetworkResult.Failure

            return networkDataSource.buyTickets(token, id, ticketAmount)
        }
    }
