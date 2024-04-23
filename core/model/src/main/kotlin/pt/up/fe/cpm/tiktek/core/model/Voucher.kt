package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Voucher {
    abstract val id: String
    abstract val userId: String
    abstract val orderId: String?

    @Serializable
    @SerialName("discount")
    data class Discount(
        override val id: String,
        val discount: Int,
        override val userId: String,
        override val orderId: String?,
    ) : Voucher()

    @Serializable
    @SerialName("free")
    data class Free(
        override val id: String,
        val itemId: String,
        override val userId: String,
        override val orderId: String?,
    ) : Voucher()
}

sealed class VoucherWithModels {
    abstract val id: String
    abstract val userId: String
    abstract val orderId: String?

    data class Discount(
        override val id: String,
        val discount: Int,
        override val userId: String,
        override val orderId: String?,
    ) : VoucherWithModels()

    data class Free(
        override val id: String,
        val item: CafeteriaItem,
        override val userId: String,
        override val orderId: String?,
    ) : VoucherWithModels()
}
