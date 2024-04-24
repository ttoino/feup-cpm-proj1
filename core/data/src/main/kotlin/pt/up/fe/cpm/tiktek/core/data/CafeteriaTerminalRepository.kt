package pt.up.fe.cpm.tiktek.core.data

import pt.up.fe.cpm.tiktek.core.model.Cart
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels

interface CafeteriaTerminalRepository {
    suspend fun sendCart(
        userId: String,
        items: Cart,
    ): NetworkResult<OrderWithModels>
}
