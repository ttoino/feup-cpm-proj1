package pt.up.fe.cpm.tiktek.backend.profile

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.put
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.auth.user
import pt.up.fe.cpm.tiktek.backend.auth.userEmail
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.User

fun Application.profileModule() {
    routing {
        authenticate {
            get("/profile") {
                val user = call.user() ?: return@get call.respond(HttpStatusCode.Forbidden)

                call.respond(user.withoutPassword())
            }

            put("/profile") {
                val currentUser = call.user() ?: return@put call.respond(HttpStatusCode.Forbidden)
                val newUser = call.receive<User>()

                val updatedUser =
                    application.database.user.update(newUser.withPassword(currentUser.password))

                call.respond(updatedUser.withoutPassword())
            }

            delete("/profile") {
                if (application.database.user.delete(call.userEmail)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.Forbidden)
                }
            }
        }
    }
}
