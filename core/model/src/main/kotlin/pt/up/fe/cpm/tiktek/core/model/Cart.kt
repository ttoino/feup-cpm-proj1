package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Cart(
    val items: Map<String, Int> = emptyMap(),
    val vouchers: Set<String> = emptySet(),
)

data class CartWithModels(
    val items: Map<CafeteriaItem, Int> = emptyMap(),
    val vouchers: Set<Voucher> = emptySet(),
)
