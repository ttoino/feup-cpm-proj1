package pt.up.fe.cpm.tiktek.backend

import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.datetime.Clock
import pt.up.fe.cpm.tiktek.backend.auth.userId
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent

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

        post("/validateTicket") {
            val body = call.receive<SendTicketRequest>()
            val id = body.ticketId

            var ticket = application.database.ticket.getById(id) ?: return@post call.respond(404)

            if (ticket.useDate != null) {
                return@post call.respond(400)
            }

            ticket = application.database.ticket.update(ticket.copy(useDate = Clock.System.now()))

            call.respond(
                TicketWithEvent(
                    id = ticket.id,
                    event = application.database.event.getById(ticket.eventId) ?: return@post call.respond(404),
                    userId = ticket.userId,
                    seat = ticket.seat,
                    purchaseDate = ticket.purchaseDate,
                    useDate = ticket.useDate,
                ),
            )
        }
    }
}
