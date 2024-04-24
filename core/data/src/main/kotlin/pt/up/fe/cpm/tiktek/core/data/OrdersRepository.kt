package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Order

interface OrdersRepository {
    fun getOrders(): Flow<List<Order>>
}
