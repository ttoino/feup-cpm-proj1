package pt.up.fe.cpm.tiktek.backend.seed

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.model.Event

fun String.slugify() = this.lowercase().replace(Regex("\\W+"), "-")

fun Faker.imageUrl() = "https://picsum.photos/seed/${random.randomString()}/512"

fun Faker.sentence() = List(random.nextInt(3, 20)) { lorem.words() }.joinToString(" ", postfix = ".")

fun Faker.paragraph() = List(random.nextInt(1, 5)) { sentence() }.joinToString(" ")

fun Faker.text() = List(random.nextInt(1, 5)) { paragraph() }.joinToString("\n")

internal val faker =
    faker {
        fakerConfig {
            locale = "pt-PT"
        }
    }.also { faker ->
        faker.randomProvider.configure {
            typeGenerator<Month> {
                faker.random.randomValue(Month.entries)
            }

            typeGenerator<LocalDate> {
                val month = faker.randomProvider.randomClassInstance<Month>()
                LocalDate(faker.random.nextInt(2024, 2026), month, faker.random.nextInt(1, month.length(false)))
            }

            typeGenerator<LocalTime> {
                LocalTime(faker.random.nextInt(0, 23), 0, 0, 0)
            }

            typeGenerator<CafeteriaItem> {
                val name =
                    faker.random.randomValue(
                        listOf(
                            faker.food.dish(),
                            faker.coffee.variety() + " coffee",
                            faker.tea.type() + " tea",
                            faker.dessert.variety(),
                            faker.beer.style(),
                        ),
                    )

                CafeteriaItem(
                    id = name.slugify(),
                    name = name,
                    price = faker.random.nextInt(1000),
                    imageUrl = faker.imageUrl(),
                )
            }

            typeGenerator<Event> {
                val name =
                    faker.random.randomValue(
                        listOf(
                            faker.show.adultMusical(),
                            faker.show.kidsMusical(),
                            faker.show.play(),
                            faker.movie.title(),
                            faker.music.bands(),
                        ),
                    )

                Event(
                    id = name.slugify(),
                    name = name,
                    description = faker.text(),
                    date = faker.randomProvider.randomClassInstance(),
                    startTime = faker.randomProvider.randomClassInstance(),
                    endTime = faker.randomProvider.randomClassInstance(),
                    location = faker.address.streetAddress(),
                    locationDetails = faker.address.streetAddress(),
                    price = faker.random.nextInt(10000),
                    imageUrl = faker.imageUrl(),
                )
            }
        }
    }
