package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
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
import pt.up.fe.cpm.tiktek.core.model.BuyTicketRequest
import pt.up.fe.cpm.tiktek.core.model.BuyTicketResponse
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.Voucher

fun Application.eventModule() {
    routing {
        authenticate {
            get("/events") {
                val events = application.database.event.getAll()

                call.respond(events)
            }

            get("/events/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)

                val event = application.database.event.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)

                call.respond(event)
            }

            post("/events/{id}/buy") {
                val body = call.receive<BuyTicketRequest>()

                val id = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)
                val event = application.database.event.getById(id) ?: return@post call.respond(HttpStatusCode.NotFound)

                val seat = application.database.ticket.getLastSeatByEventId(id)?.toIntOrNull() ?: 0

                val tickets =
                    application.database.ticket.createAll(
                        List(body.ticketAmount) {
                            Ticket(uuid(), id, call.userId, (seat + it + 1).toString(), Clock.System.now(), null)
                        },
                    )

                // Add discount voucher for each 200 eur
                val total = event.price * body.ticketAmount
                val discountVouchers = total / 200_00
                val vouchers =
                    application.database.voucher.createAll(
                        List(discountVouchers) {
                            Voucher.Discount(uuid(), 5, call.userId, null)
                        },
                    ).toMutableList()

                // Add free voucher for each ticket
                vouchers +=
                    application.database.voucher.createAll(
                        List(body.ticketAmount) {
                            Voucher.Free(
                                uuid(),
                                application.database.cafeteriaItem.getRandom().id,
                                call.userId,
                                null,
                            )
                        },
                    )

                call.respond(
                    BuyTicketResponse(
                        tickets = tickets,
                        vouchers = vouchers,
                    ),
                )
            }
        }
    }
}
