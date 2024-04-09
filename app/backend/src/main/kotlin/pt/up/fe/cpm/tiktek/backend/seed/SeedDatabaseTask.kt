package pt.up.fe.cpm.tiktek.backend.seed

import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.coroutines.runBlocking
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event

fun Application.seedDatabase() =
    runBlocking {
        if (database.cafeteriaItem.getAll().isEmpty()) {
            log.info("Seeding database with cafeteria items")

            database.cafeteriaItem.createAll(
                List(10) {
                    faker.randomProvider.randomClassInstance<CafeteriaItem>()
                },
            )
        }

        if (database.event.getAll().isEmpty()) {
            log.info("Seeding database with events")

            database.event.createAll(
                List(1000) {
                    faker.randomProvider.randomClassInstance<Event>()
                },
            )
        }
    }
