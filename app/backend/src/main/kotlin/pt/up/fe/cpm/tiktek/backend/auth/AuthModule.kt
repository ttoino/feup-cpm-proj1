package pt.up.fe.cpm.tiktek.backend.auth

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.routing.routing

fun Application.authModule() {
    jwtModule()

    install(RequestValidation) {
        loginRequest()
        registerRequest()
    }

    routing {
        loginRoute()
        registerRoute()
    }
}
