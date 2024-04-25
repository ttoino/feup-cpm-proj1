package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import pt.up.fe.cpm.tiktek.core.data.KeysRepository
import pt.up.fe.cpm.tiktek.core.data.OrdersRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.work.Deletable
import pt.up.fe.cpm.tiktek.core.data.work.Syncable
import pt.up.fe.cpm.tiktek.core.local.LocalOrdersDataSource
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstOrdersRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalOrdersDataSource,
        private val userRepository: UserRepository,
        private val keysRepository: KeysRepository,
    ) : OrdersRepository, Syncable, Deletable {
        override suspend fun sync(): NetworkResult<Unit> {
            val token = userRepository.getToken().first() ?: return NetworkResult.Failure

            val result = networkDataSource.getOrders(token, keysRepository.privateKey)

            result.getOrNull()?.let {
                localDataSource.insert(it)
            }

            return result.map {}
        }

        override suspend fun delete() {
            localDataSource.deleteOrders()
        }

        override fun getOrders(): Flow<List<Order>> = localDataSource.getOrders()
    }
