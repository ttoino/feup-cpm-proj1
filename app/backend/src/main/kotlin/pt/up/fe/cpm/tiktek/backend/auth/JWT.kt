package pt.up.fe.cpm.tiktek.backend.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.backend.isEmailAddress
import pt.up.fe.cpm.tiktek.core.model.AuthResponse

lateinit var secret: String

fun Application.jwtModule() {
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "SECRET FOR DEVELOPMENT PURPOSES ONLY!"

    install(Authentication) {
        jwt {
            verifier(JWT.require(Algorithm.HMAC512(secret)).build())

            validate { credential ->
                if (credential.payload.getClaim("email").asString().isEmailAddress()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

suspend fun ApplicationCall.respondWithToken(email: String) {
    respond(AuthResponse(JWT.create().withClaim("email", email).sign(Algorithm.HMAC512(secret))))
}

val ApplicationCall.userEmail
    get() =
        authentication.principal<JWTPrincipal>()?.payload?.getClaim(
            "email",
        )?.asString() ?: throw IllegalStateException("No user authenticated")

suspend fun ApplicationCall.user() = application.database.user.getByEmail(userEmail)
