package pt.up.fe.cpm.tiktek.backend.cafeteria

import io.ktor.server.application.Application
import io.ktor.server.application.application
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import pt.up.fe.cpm.tiktek.backend.di.database

fun Application.cafeteriaModule() {
    routing {
        authenticate {
            get("/cafeteria") {
                val cafeteria = application.database.cafeteriaItem.getAll()

                call.respond(cafeteria)
            }

            get("/cafeteria/{id}") {
                val id = call.parameters["id"] ?: return@get call.respond(400)

                val cafeteria = application.database.cafeteriaItem.getById(id) ?: return@get call.respond(404)

                call.respond(cafeteria)
            }
        }
    }
}
