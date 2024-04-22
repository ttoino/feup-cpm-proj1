package pt.up.fe.cpm.tiktek.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: String,
    val eventId: String,
    val userEmail: String,
    val seat: String,
    val purchaseDate: Instant,
)

data class TicketWithEvent(
    val id: String,
    val event: Event,
    val userEmail: String,
    val seat: String,
    val purchaseDate: Instant,
)
