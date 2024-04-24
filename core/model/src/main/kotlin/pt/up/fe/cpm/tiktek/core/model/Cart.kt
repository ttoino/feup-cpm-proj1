package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val items: Map<String, Int> = emptyMap(),
    val vouchers: Set<String> = emptySet(),
)

data class CartWithModels(
    val items: Map<CafeteriaItem, Int> = emptyMap(),
    val vouchers: Set<VoucherWithModels> = emptySet(),
) {
    val subtotal get() = items.map { (item, quantity) -> item.price * quantity }.sum()

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
