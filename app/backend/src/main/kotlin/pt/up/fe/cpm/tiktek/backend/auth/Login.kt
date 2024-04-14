package pt.up.fe.cpm.tiktek.backend.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.LoginRequest

val verifier: BCrypt.Verifyer = BCrypt.verifyer()

fun Route.loginRoute() {
    post("/login") {
        val request = call.receive<LoginRequest>()

        val user =
            application.database.user.getByEmail(request.email)
                ?: return@post call.respond(HttpStatusCode.Forbidden)

        if (verifier.verify(request.password.toCharArray(), user.password).verified) {
            call.respondWithToken(request.email)
        } else {
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}
