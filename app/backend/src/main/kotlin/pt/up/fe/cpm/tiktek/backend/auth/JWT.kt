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
import io.ktor.server.request.header
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.PublicKeySerializer
import java.security.PublicKey
import java.security.Signature
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

lateinit var secret: String
private val sign = Signature.getInstance("SHA256withRSA")

@OptIn(ExperimentalEncodingApi::class)
fun Application.jwtModule() {
    secret = environment.config.propertyOrNull("jwt.secret")?.getString() ?: "SECRET FOR DEVELOPMENT PURPOSES ONLY!"

    install(Authentication) {
        jwt {
            verifier(JWT.require(Algorithm.HMAC512(secret)).build())

            validate { credential ->
                val id = credential.payload.getClaim("id").asString()
                val key = Json.decodeFromString(PublicKeySerializer, credential.payload.getClaim("key").asString())

                val salt =
                    Base64.decode(
                        request.header("X-Salt") ?: throw IllegalStateException("No salt provided"),
                    )
                val body = receive<ByteArray>()
                val content = salt + body
                val signature =
                    Base64.decode(
                        request.header("X-Signature") ?: throw IllegalStateException("No signature provided"),
                    )

                val verified =
                    sign.run {
                        initVerify(key)
                        update(content)
                        verify(signature)
                    }

                if (application.database.user.existsById(id) && verified) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}

suspend fun ApplicationCall.respondWithToken(
    id: String,
    key: PublicKey,
) {
    respond(
        AuthResponse(
            JWT.create()
                .withClaim("id", id)
                .withClaim("key", Json.encodeToString(PublicKeySerializer, key))
                .sign(Algorithm.HMAC512(secret)),
        ),
    )
}

val ApplicationCall.userId
    get() =
        authentication.principal<JWTPrincipal>()?.payload?.getClaim(
            "id",
        )?.asString() ?: throw IllegalStateException("No user authenticated")

suspend fun ApplicationCall.user() = application.database.user.getById(userId)
