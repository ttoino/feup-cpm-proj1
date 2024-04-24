package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val items: List<OrderItem>,
)

@Serializable
data class OrderWithModels(
    val id: String,
    val userId: String,
    val items: List<OrderItemWithModels>,
    val vouchers: List<Voucher>,
)

@Serializable
data class OrderItem(
    val itemId: String,
    val quantity: Int,
)

@Serializable
data class OrderItemWithModels(
    val item: CafeteriaItem,
    val quantity: Int,
)
