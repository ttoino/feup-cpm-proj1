package pt.up.fe.cpm.tiktek.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val eventId: String,
    val userId: String,
    val purchaseDate: Instant,
)
