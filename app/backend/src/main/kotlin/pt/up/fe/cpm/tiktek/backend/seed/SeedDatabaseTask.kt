package pt.up.fe.cpm.tiktek.backend.seed

import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.coroutines.runBlocking
import pt.up.fe.cpm.tiktek.backend.di.database

fun Application.seedDatabase() =
    runBlocking {
        if (database.cafeteriaItem.getAll().isEmpty()) {
            log.info("Seeding database with cafeteria items")

            database.cafeteriaItem.createAll(
                List(10) {
                    faker.cafeteriaItem()
                },
            )
        }

        if (database.event.getAll().isEmpty()) {
            log.info("Seeding database with events")

            database.event.createAll(
                List(200) {
                    faker.event()
                },
            )
        }
    }
