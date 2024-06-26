package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.serialization.Serializable

@Validate
@Serializable
data class SendTicketRequest(
    val ticketId: String,
)
