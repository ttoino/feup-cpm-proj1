package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import io.ktor.util.AttributeKey
import kotlinx.serialization.Contextual
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import pt.up.fe.cpm.tiktek.backend.auth.authModule
import pt.up.fe.cpm.tiktek.backend.cafeteria.cafeteriaModule
import pt.up.fe.cpm.tiktek.backend.event.eventModule
import pt.up.fe.cpm.tiktek.backend.order.orderModule
import pt.up.fe.cpm.tiktek.backend.profile.profileModule
import pt.up.fe.cpm.tiktek.backend.seed.seedDatabase
import pt.up.fe.cpm.tiktek.backend.ticket.ticketModule
import pt.up.fe.cpm.tiktek.backend.voucher.voucherModule

val DETAILS_KEY = AttributeKey<String>("details")
val DATA_KEY = AttributeKey<JsonElement>("data")
val STATUS_PAGES_KEY = AttributeKey<Unit>("StatusPagesTriggered")

@Serializable
data class ErrorResponse(
    val status: Int,
    val title: String,
    val details: String? = null,
    @Contextual val data: JsonElement? = null,
)

@OptIn(ExperimentalSerializationApi::class)
fun main() {
    embeddedServer(Netty, port = 8080) {
        seedDatabase()

        install(CallLogging)
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = false
                    isLenient = true
                    ignoreUnknownKeys = true
                    explicitNulls = false
                },
            )
        }
        install(StatusPages) {
            status(*HttpStatusCode.allStatusCodes.filter { !it.isSuccess() }.toTypedArray()) { call, status ->
                call.application.log.error("StatusCode")
                call.respond(
                    status,
                    ErrorResponse(
                        status.value,
                        status.description,
                        call.attributes.getOrNull(DETAILS_KEY),
                        call.attributes.getOrNull(DATA_KEY),
                    ),
                )
            }
            exception<Throwable> { call, cause ->
                call.application.log.error("Throwable", cause)
                cause.message?.let {
                    call.attributes.put(DETAILS_KEY, it)
                }

                when (cause) {
                    is RequestValidationException -> call.attributes.put(DATA_KEY, JsonArray(cause.reasons.map { JsonPrimitive(it) }))
                }

                call.attributes.remove(STATUS_PAGES_KEY)
            }
        }

        authModule()
        cafeteriaModule()
        eventModule()
        orderModule()
        profileModule()
        ticketModule()
        validationModule()
        voucherModule()
    }.start(true)
}
