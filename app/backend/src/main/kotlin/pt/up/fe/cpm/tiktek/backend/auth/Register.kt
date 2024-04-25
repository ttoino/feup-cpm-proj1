package pt.up.fe.cpm.tiktek.backend.auth

import at.favre.lib.crypto.bcrypt.BCrypt
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import pt.up.fe.cpm.tiktek.backend.RequestViolationException
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.backend.uuid
import pt.up.fe.cpm.tiktek.core.model.PartialRegisterRequest
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.UserWithPassword
import pt.up.fe.cpm.tiktek.core.model.validation.Violation

private val hasher: BCrypt.Hasher = BCrypt.withDefaults()

fun Route.registerRoute() {
    post("/register") {
        val request = call.receive<RegisterRequest>()

        if (application.database.user.existsByEmail(request.email)) {
            throw RequestViolationException(request, Violation.EMAIL_ALREADY_EXISTS)
        }

        val user =
            UserWithPassword(
                id = uuid(),
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

        call.respondWithToken(createdUser.id, request.key)
    }

    post("/partial-register") {
        val request = call.receive<PartialRegisterRequest>()

        if (application.database.user.existsByEmail(request.email)) {
            throw RequestViolationException(request, Violation.EMAIL_ALREADY_EXISTS)
        }

        call.respond(HttpStatusCode.OK)
    }
}
