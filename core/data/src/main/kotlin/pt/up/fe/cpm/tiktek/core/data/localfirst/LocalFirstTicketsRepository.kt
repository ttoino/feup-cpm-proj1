package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.local.LocalTicketsDataSource
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstTicketsRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalTicketsDataSource,
        private val userRepository: UserRepository,
    ) : TicketsRepository {
        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun sync() {
            userRepository.getToken().mapLatest {
                it?.let {
                    networkDataSource.getTickets(it).getOrNull()?.let {
                        localDataSource.insert(it)
                    }
                }
            }.first()
        }

        override fun getTickets(): Flow<List<Ticket>> = localDataSource.getTickets()

        override fun getTicket(id: String): Flow<Ticket> = localDataSource.getTicket(id)
    }
