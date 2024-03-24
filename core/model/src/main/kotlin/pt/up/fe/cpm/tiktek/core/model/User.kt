package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
)

@Serializable
data class UserWithPassword(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
)
