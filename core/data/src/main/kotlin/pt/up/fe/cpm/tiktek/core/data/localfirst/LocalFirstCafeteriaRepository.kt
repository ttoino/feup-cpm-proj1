package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.KeysRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.work.Syncable
import pt.up.fe.cpm.tiktek.core.local.LocalCafeteriaDataSource
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstCafeteriaRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalCafeteriaDataSource,
        private val userRepository: UserRepository,
        private val keysRepository: KeysRepository,
    ) :
    CafeteriaRepository, Syncable {
        override suspend fun sync(): NetworkResult<Unit> {
            val token = userRepository.getToken().first() ?: return NetworkResult.Failure

            val result = networkDataSource.getCafeteriaItems(token, keysRepository.privateKey)

            result.getOrNull()?.let {
                localDataSource.insert(it)
            }

            return result.map {}
        }

        override fun getCafeteriaItems(): Flow<List<CafeteriaItem>> = localDataSource.getCafeteriaItems()

        override fun getCafeteriaItem(id: String): Flow<CafeteriaItem> = localDataSource.getCafeteriaItem(id)
    }
