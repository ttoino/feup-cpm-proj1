package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Voucher {
    abstract val id: String
    abstract val userEmail: String
    abstract val orderId: String?

    @Serializable
    @SerialName("discount")
    data class Discount(
        override val id: String,
        val discount: Int,
        override val userEmail: String,
        override val orderId: String?,
    ) : Voucher()

    @Serializable
    @SerialName("free")
    data class Free(
        override val id: String,
        val itemId: String,
        override val userEmail: String,
        override val orderId: String?,
    ) : Voucher()
}
