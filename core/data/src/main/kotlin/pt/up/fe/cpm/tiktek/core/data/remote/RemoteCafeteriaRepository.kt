package pt.up.fe.cpm.tiktek.core.data.remote

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import timber.log.Timber
import javax.inject.Inject

class RemoteCafeteriaRepository
    @Inject
    constructor(private val networkDataSource: NetworkDataSource, private val userRepository: UserRepository) :
    CafeteriaRepository {
        override fun getCafeteriaItems(): Flow<List<CafeteriaItem>> =
            userRepository.getToken().map {
                when (it) {
                    null -> emptyList()
                    else ->
                        try {
                            networkDataSource.getCafeteriaItems(it)
                        } catch (e: Exception) {
                            Timber.w(e)
                            emptyList()
                        }
                }
            }
    }
