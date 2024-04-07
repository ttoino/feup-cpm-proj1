package pt.up.fe.cpm.tiktek.backend.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.application
import io.ktor.server.routing.post
import kotlinx.serialization.Serializable
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.backend.isCreditCardNumber
import pt.up.fe.cpm.tiktek.backend.isCvv
import pt.up.fe.cpm.tiktek.backend.isDate
import pt.up.fe.cpm.tiktek.backend.isEmailAddress
import pt.up.fe.cpm.tiktek.backend.isExpirationDate
import pt.up.fe.cpm.tiktek.backend.isNif
import pt.up.fe.cpm.tiktek.backend.isPassword
import pt.up.fe.cpm.tiktek.core.model.UserWithPassword

fun RequestValidationConfig.registerRequest() {
    validate<RegisterRequest> {
        if (it.name.isBlank()) {
            ValidationResult.Invalid("Invalid name")
        } else if (!it.nif.isNif()) {
            ValidationResult.Invalid("Invalid NIF")
        } else if (!it.birthdate.isDate()) {
            ValidationResult.Invalid("Invalid birthdate")
        } else if (!it.email.isEmailAddress()) {
            ValidationResult.Invalid("Invalid email")
        } else if (!it.password.isPassword()) {
            ValidationResult.Invalid("Invalid password")
        } else if (it.nameCc.isBlank()) {
            ValidationResult.Invalid("Invalid name on credit card")
        } else if (!it.numberCc.isCreditCardNumber()) {
            ValidationResult.Invalid("Invalid number on credit card")
        } else if (!it.expirationDateCc.isExpirationDate()) {
            ValidationResult.Invalid("Invalid expiration date on credit card")
        } else if (!it.cvvCc.isCvv()) {
            ValidationResult.Invalid("Invalid CVV on credit card")
        } else {
            ValidationResult.Valid
        }
    }
}

@Serializable
data class RegisterRequest(
    val name: String,
    val nif: String,
    val birthdate: String,
    val email: String,
    val password: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
)

val hasher: BCrypt.Hasher = BCrypt.withDefaults()

fun Route.registerRoute() {
    post("/register") {
        val request = call.receive<RegisterRequest>()

        val user =
            UserWithPassword(
                name = request.name,
                email = request.email,
                password = hasher.hashToString(12, request.password.toCharArray()),
            )

        val createdUser = application.database.user.create(user)

        call.respondWithToken(createdUser.email)
    }
}
