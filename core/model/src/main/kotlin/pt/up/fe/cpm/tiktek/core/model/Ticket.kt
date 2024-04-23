package pt.up.fe.cpm.tiktek.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: String,
    val eventId: String,
    val userId: String,
    val seat: String,
    val purchaseDate: Instant,
    val useDate: Instant?,
)

data class TicketWithEvent(
    val id: String,
    val event: Event,
    val userId: String,
    val seat: String,
    val purchaseDate: Instant,
    val useDate: Instant?,
)
