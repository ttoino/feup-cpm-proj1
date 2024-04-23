package pt.up.fe.cpm.tiktek.backend

import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.auth.userId
import pt.up.fe.cpm.tiktek.backend.di.database

fun Application.ticketModule() {
    routing {
        authenticate {
            get("/tickets") {
                val tickets = application.database.ticket.getAllByUser(call.userId)

                call.respond(tickets)
            }

            get("/ticket/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(400)

                val ticket = application.database.ticket.getById(id) ?: return@get call.respond(404)

                call.respond(ticket)
            }
        }
    }
}
