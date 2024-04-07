package pt.up.fe.cpm.tiktek.backend.order

import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.auth.userEmail
import pt.up.fe.cpm.tiktek.backend.di.database

fun Application.orderModule() {
    routing {
        authenticate {
            get("/orders") {
                val orders = application.database.order.getAllByUser(call.userEmail)

                call.respond(orders)
            }

            get("/order/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(400)

                val order = application.database.order.getById(id) ?: return@get call.respond(404)

                call.respond(order)
            }
        }
    }
}
