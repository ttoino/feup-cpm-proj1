package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class CafeteriaItem(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
)
