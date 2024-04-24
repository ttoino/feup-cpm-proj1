package pt.up.fe.cpm.tiktek.core.data

import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.SendCartRequest

interface CafeteriaTerminalRepository {
    suspend fun sendCart(request: SendCartRequest): NetworkResult<OrderWithModels>
}
