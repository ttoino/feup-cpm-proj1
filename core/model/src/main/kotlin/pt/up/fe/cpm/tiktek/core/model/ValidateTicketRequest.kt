package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidateTicketRequest(
    val ticketId: String,
)
