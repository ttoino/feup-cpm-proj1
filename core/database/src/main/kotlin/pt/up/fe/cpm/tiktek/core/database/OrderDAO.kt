package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.Order

interface OrderDAO {
    suspend fun getAllByUser(userId: String): List<Order>

    suspend fun getById(id: String): Order?

    suspend fun create(order: Order): Order

    suspend fun update(order: Order): Order

    suspend fun delete(id: String): Boolean
}
