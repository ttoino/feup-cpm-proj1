package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val items: List<OrderItem>,
)

@Serializable
data class OrderItem(
    val itemId: String,
    val quantity: Int,
)
