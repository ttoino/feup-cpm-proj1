package pt.up.fe.cpm.tiktek.backend.auth

import io.ktor.server.application.Application
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.routing.routing

fun RequestValidationConfig.validateAuth() {
    validateLogin()
    validateRegister()
}

fun Application.authModule() {
    jwtModule()

    routing {
        loginRoute()
        registerRoute()
    }
}
