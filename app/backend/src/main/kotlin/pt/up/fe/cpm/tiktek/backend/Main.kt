package pt.up.fe.cpm.tiktek.backend

import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.routing.routing

fun main() {
    embeddedServer(Netty) {
        install(CallLogging)
        install(Authentication)
        routing {
        }
    }.start(true)
}
