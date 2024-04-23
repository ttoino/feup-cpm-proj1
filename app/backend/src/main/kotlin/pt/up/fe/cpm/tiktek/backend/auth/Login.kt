package pt.up.fe.cpm.tiktek.backend.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import pt.up.fe.cpm.tiktek.backend.RequestViolationException
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.LoginRequest
import pt.up.fe.cpm.tiktek.core.model.validation.Violation

val verifier: BCrypt.Verifyer = BCrypt.verifyer()

fun Route.loginRoute() {
    post("/login") {
        val request = call.receive<LoginRequest>()

        val user =
            application.database.user.getByEmail(request.email)
                ?: throw RequestViolationException(request, Violation.LOGIN, HttpStatusCode.Forbidden)

        if (verifier.verify(request.password.toCharArray(), user.password).verified) {
            call.respondWithToken(user.id)
        } else {
            throw RequestViolationException(request, Violation.LOGIN, HttpStatusCode.Forbidden)
        }
    }
}
