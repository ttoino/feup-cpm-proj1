package pt.up.fe.cpm.tiktek.backend.seed

import io.ktor.server.application.Application
import io.ktor.server.application.log
import kotlinx.coroutines.runBlocking
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem

fun Application.seedDatabase() =
    runBlocking {
        if (database.cafeteriaItem.getAll().isEmpty()) {
            log.info("Seeding database with cafeteria items")

            database.cafeteriaItem.createAll(
                listOf(
                    CafeteriaItem(
                        "sandwich",
                        "Sanduíche",
                        250,
                        "https://i.pinimg.com/564x/c4/bb/17/c4bb17f8b557db8cbfe0ed5ac8514eea.jpg",
                    ),
                    CafeteriaItem(
                        "popcorn",
                        "Pipocas",
                        100,
                        "https://i.pinimg.com/564x/02/dd/6a/02dd6a9440587210747532a5f47c9ab1.jpg",
                    ),
                    CafeteriaItem(
                        "soda",
                        "Refrigerante",
                        120,
                        "https://i.pinimg.com/564x/51/82/74/5182744fbb7b45c7cce55f0974b445ee.jpg",
                    ),
                    CafeteriaItem(
                        "coffee",
                        "Café",
                        50,
                        "https://i.pinimg.com/564x/6f/6e/ea/6f6eea526c10e982107f78775b7c2210.jpg",
                    ),
                ),
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
