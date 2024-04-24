package pt.up.fe.cpm.tiktek.core.data.remote

import pt.up.fe.cpm.tiktek.core.data.CafeteriaTerminalRepository
import pt.up.fe.cpm.tiktek.core.model.Cart
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class RemoteCafeteriaTerminalRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
    ) : CafeteriaTerminalRepository {
        override suspend fun sendCart(
            userId: String,
            items: Cart,
        ): NetworkResult<OrderWithModels> {
            val result =
                networkDataSource.sendCart(
                    userId,
                    items,
                )

            return result
        }
    }
