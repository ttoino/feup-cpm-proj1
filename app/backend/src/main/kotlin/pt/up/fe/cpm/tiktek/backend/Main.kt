package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import pt.up.fe.cpm.tiktek.backend.auth.authModule

@Serializable
data class ErrorResponse(val status: Int, val title: String)

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(CallLogging)
        install(ContentNegotiation) {
            json()
        }
        install(StatusPages) {
            status(*HttpStatusCode.allStatusCodes.filter { !it.isSuccess() }.toTypedArray()) { call, status ->
                call.respond(status, ErrorResponse(status.value, status.description))
            }
        }

        authModule()
    }.start(true)
}
