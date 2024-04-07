package pt.up.fe.cpm.tiktek.backend.event

import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.di.database

fun Application.eventModule() {
    routing {
        authenticate {
            get("/events") {
                val events = application.database.event.getAll()

                call.respond(events)
            }

            get("/events/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(400)

                val event = application.database.event.getById(id) ?: return@get call.respond(404)

                call.respond(event)
            }
        }
    }
}
