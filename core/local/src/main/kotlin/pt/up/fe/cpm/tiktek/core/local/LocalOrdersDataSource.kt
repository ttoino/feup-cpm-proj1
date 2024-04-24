package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Order

interface LocalOrdersDataSource {
    fun getOrders(): Flow<List<Order>>

    suspend fun insert(orders: List<Order>)

    suspend fun deleteOrders()
}
