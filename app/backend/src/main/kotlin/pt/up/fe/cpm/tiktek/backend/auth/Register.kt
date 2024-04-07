package pt.up.fe.cpm.tiktek.backend.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.backend.isCreditCardNumber
import pt.up.fe.cpm.tiktek.backend.isCvv
import pt.up.fe.cpm.tiktek.backend.isEmailAddress
import pt.up.fe.cpm.tiktek.backend.isExpirationDate
import pt.up.fe.cpm.tiktek.backend.isNif
import pt.up.fe.cpm.tiktek.backend.isPassword
import pt.up.fe.cpm.tiktek.backend.validates
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.UserWithPassword

fun RequestValidationConfig.registerRequest() {
    validates<RegisterRequest> {
        validate(it.name.isNotBlank(), "Invalid name")
        validate(it.nif.isNif(), "Invalid NIF")
        validate(it.email.isEmailAddress(), "Invalid email")
        validate(it.password.isPassword(), "Invalid password")
        validate(it.nameCc.isNotBlank(), "Invalid name on credit card")
        validate(it.numberCc.isCreditCardNumber(), "Invalid number on credit card")
        validate(it.expirationDateCc.isExpirationDate(), "Invalid expiration date on credit card")
        validate(it.cvvCc.isCvv(), "Invalid CVV on credit card")
    }
}

val hasher: BCrypt.Hasher = BCrypt.withDefaults()

fun Route.registerRoute() {
    post("/register") {
        val request = call.receive<RegisterRequest>()

        val user =
            UserWithPassword(
                name = request.name,
                nif = request.nif,
                birthdate = request.birthdate,
                email = request.email,
                nameCc = request.nameCc,
                numberCc = request.numberCc,
                expirationDateCc = request.expirationDateCc,
                cvvCc = request.cvvCc,
                password = hasher.hashToString(12, request.password.toCharArray()),
            )

        val createdUser = application.database.user.create(user)

        call.respondWithToken(createdUser.email)
    }
}
