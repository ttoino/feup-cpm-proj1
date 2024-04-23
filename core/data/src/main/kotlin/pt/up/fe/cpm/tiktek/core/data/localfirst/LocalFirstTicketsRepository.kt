package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.work.Deletable
import pt.up.fe.cpm.tiktek.core.data.work.Syncable
import pt.up.fe.cpm.tiktek.core.local.LocalTicketsDataSource
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstTicketsRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalTicketsDataSource,
        private val userRepository: UserRepository,
    ) : TicketsRepository, Syncable, Deletable {
        override suspend fun sync(): NetworkResult<Unit> {
            val token = userRepository.getToken().first() ?: return NetworkResult.Failure

            val result = networkDataSource.getTickets(token)

            result.getOrNull()?.let {
                localDataSource.insert(it)
            }

            return result.map {}
        }

        override suspend fun delete() {
            localDataSource.deleteTickets()
        }

        override fun getTickets(): Flow<List<Ticket>> = localDataSource.getTickets()

        override fun getTicket(id: String): Flow<Ticket> = localDataSource.getTicket(id)
    }
