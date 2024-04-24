package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.serialization.Serializable

@Validate
@Serializable
data class BuyTicketRequest(
    val ticketAmount: Int,
)

@Serializable
data class BuyTicketResponse(
    val tickets: List<Ticket>,
    val vouchers: List<Voucher>,
)