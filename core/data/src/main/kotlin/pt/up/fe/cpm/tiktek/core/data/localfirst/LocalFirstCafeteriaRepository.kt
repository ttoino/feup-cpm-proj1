package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.local.LocalCafeteriaDataSource
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstCafeteriaRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalCafeteriaDataSource,
        private val userRepository: UserRepository,
    ) :
    CafeteriaRepository {
        @OptIn(ExperimentalCoroutinesApi::class)
        override suspend fun sync() {
            userRepository.getToken().mapLatest {
                it?.let {
                    networkDataSource.getCafeteriaItems(it).getOrNull()?.let {
                        localDataSource.insert(it)
                    }
                }
            }.first()
        }

        override fun getCafeteriaItems(): Flow<List<CafeteriaItem>> = localDataSource.getCafeteriaItems()
    }
