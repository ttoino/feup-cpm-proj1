package pt.up.fe.cpm.tiktek.backend.auth

import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.authModule() {
    jwtModule()

    routing {
        loginRoute()
        registerRoute()
    }
}
