package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(val token: String)

@Validate
@Serializable
data class LoginRequest(val email: String, val password: String)

@Validate
@Serializable
data class RegisterRequest(
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
    val password: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
)
