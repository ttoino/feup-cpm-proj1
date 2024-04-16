package pt.up.fe.cpm.tiktek.backend

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import pt.up.fe.cpm.tiktek.backend.auth.authModule
import pt.up.fe.cpm.tiktek.backend.cafeteria.cafeteriaModule
import pt.up.fe.cpm.tiktek.backend.event.eventModule
import pt.up.fe.cpm.tiktek.backend.order.orderModule
import pt.up.fe.cpm.tiktek.backend.profile.profileModule
import pt.up.fe.cpm.tiktek.backend.seed.seedDatabase
import pt.up.fe.cpm.tiktek.backend.ticket.ticketModule
import pt.up.fe.cpm.tiktek.backend.voucher.voucherModule

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

        authModule()
        cafeteriaModule()
        eventModule()
        errorModule()
        orderModule()
        profileModule()
        ticketModule()
        validationModule()
        voucherModule()
    }.start(true)
}
