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

fun Faker.month() = random.randomValue(Month.entries)

fun Faker.date() = month().let { LocalDate(random.nextInt(2024, 2025), it, random.nextInt(1, it.length(false))) }

fun Faker.time() = LocalTime(random.nextInt(0, 23), 0, 0, 0)

fun Faker.cafeteriaItem() =
    random.randomValue(
        listOf(
            food.unique::dish,
            { coffee.unique.variety() + " coffee" },
            { tea.unique.type() + " tea" },
            dessert.unique::variety,
            { beer.unique.style() + " beer" },
        ),
    )().let { name ->
        CafeteriaItem(
            id = name.slugify(),
            name = name,
            price = random.nextInt(1000),
            imageUrl = imageUrl(),
        )
    }

fun Faker.event() =
    random.randomValue(
        listOf(
            show.unique::adultMusical,
            show.unique::kidsMusical,
            show.unique::play,
            movie.unique::title,
            music.unique::bands,
        ),
    )().let { name ->
        Event(
            id = name.slugify(),
            name = name,
            description = text(),
            date = date(),
            startTime = time(),
            endTime = time(),
            location = address.streetAddress(),
            locationDetails = address.streetAddress(),
            price = random.nextInt(10000),
            imageUrl = imageUrl(),
        )
    }

internal val faker =
    faker {
        fakerConfig {
            locale = "pt-PT"
            randomSeed = 42
        }
    }
