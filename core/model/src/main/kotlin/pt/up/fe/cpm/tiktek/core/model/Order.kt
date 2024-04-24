package pt.up.fe.cpm.tiktek.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: String,
    val userId: String,
    val items: List<OrderItem>,
    val date: Instant,
)

@Serializable
data class OrderWithModels(
    val id: String,
    val userId: String,
    val items: List<OrderItemWithModels>,
    val vouchers: List<VoucherWithModels>,
    val date: Instant,
) {
    val subtotal get() = items.sumOf { (item, quantity) -> item.price * quantity }

    val total get() =
        vouchers.sortedBy {
            when (it) {
                is VoucherWithModels.Free -> 0
                is VoucherWithModels.Discount -> 1
            }
        }.fold(subtotal) { subtotal, voucher ->
            when (voucher) {
                is VoucherWithModels.Free -> maxOf(0, subtotal - voucher.item.price)
                is VoucherWithModels.Discount -> subtotal * (1 - voucher.discount)
            }
        }
}

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
