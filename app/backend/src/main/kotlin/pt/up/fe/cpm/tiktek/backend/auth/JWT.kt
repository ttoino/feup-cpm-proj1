package pt.up.fe.cpm.tiktek.backend.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import pt.up.fe.cpm.tiktek.backend.isEmailAddress

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

@Serializable
data class JWTResponse(val token: String)

suspend fun ApplicationCall.respondWithToken(email: String) {
    respond(JWTResponse(JWT.create().withClaim("email", email).sign(Algorithm.HMAC512(secret))))
}
