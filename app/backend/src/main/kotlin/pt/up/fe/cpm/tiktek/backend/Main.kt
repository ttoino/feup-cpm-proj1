package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import kotlinx.serialization.Serializable
import pt.up.fe.cpm.tiktek.backend.auth.authModule
import pt.up.fe.cpm.tiktek.backend.auth.validateAuth
import pt.up.fe.cpm.tiktek.backend.cafeteria.cafeteriaModule
import pt.up.fe.cpm.tiktek.backend.event.eventModule
import pt.up.fe.cpm.tiktek.backend.order.orderModule
import pt.up.fe.cpm.tiktek.backend.profile.profileModule
import pt.up.fe.cpm.tiktek.backend.profile.validateProfile
import pt.up.fe.cpm.tiktek.backend.ticket.ticketModule
import pt.up.fe.cpm.tiktek.backend.voucher.voucherModule

@Serializable
data class ErrorResponse(val status: Int, val title: String, val details: String? = null)

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
            exception<RequestValidationException> { call, cause ->
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(HttpStatusCode.BadRequest.value, HttpStatusCode.BadRequest.description, cause.reasons.joinToString("\n")),
                )
            }
        }
        install(RequestValidation) {
            validateAuth()
            validateProfile()
        }

        authModule()
        cafeteriaModule()
        eventModule()
        orderModule()
        profileModule()
        ticketModule()
        voucherModule()
    }.start(true)
}
