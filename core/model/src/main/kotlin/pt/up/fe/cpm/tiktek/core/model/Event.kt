package pt.up.fe.cpm.tiktek.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Event(
    val id: String,
    val name: String,
    val shortDescription: String,
    val description: String,
    val date: Instant,
    val location: String,
    val imageUrl: String,
)
