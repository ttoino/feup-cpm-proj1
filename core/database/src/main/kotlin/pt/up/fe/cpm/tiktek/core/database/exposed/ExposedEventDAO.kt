package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.kotlin.datetime.time
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.EventDAO
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject

class ExposedEventDAO
    @Inject
    constructor(
        private val db: ExposedDatabaseConnection,
    ) : EventDAO {
        override suspend fun getAll(): List<Event> =
            db.query {
                Events.selectAll().map { it.toEvent() }
            }

        override suspend fun getById(id: String): Event? =
            db.query {
                Events.selectAll().where { Events.id eq id }.map { it.toEvent() }.firstOrNull()
            }

        override suspend fun create(event: Event) =
            db.query {
                Events.insert {
                    it.fromEvent(event)
                }
                event
            }

        override suspend fun update(event: Event) =
            db.query {
                Events.update({ Events.id eq event.id }) {
                    it.fromEvent(event)
                }
                event
            }

        override suspend fun delete(id: String) =
            db.query {
                Events.deleteWhere { Events.id eq id } > 0
            }
    }

private fun ResultRow.toEvent() =
    Event(
        id = this[Events.id],
        name = this[Events.name],
        description = this[Events.description],
        date = this[Events.date],
        startTime = this[Events.startTime],
        endTime = this[Events.endTime],
        location = this[Events.location],
        locationDetails = this[Events.locationDetails],
        price = this[Events.price],
        imageUrl = this[Events.imageUrl],
    )

private fun UpdateBuilder<*>.fromEvent(event: Event) =
    apply {
        this[Events.id] = event.id
        this[Events.name] = event.name
        this[Events.description] = event.description
        this[Events.date] = event.date
        this[Events.startTime] = event.startTime
        this[Events.endTime] = event.endTime
        this[Events.location] = event.location
        this[Events.locationDetails] = event.locationDetails
        this[Events.price] = event.price
        this[Events.imageUrl] = event.imageUrl
    }

internal object Events : Table() {
    val id = varchar("id", 128)
    val name = varchar("name", 255)
    val description = text("description")
    val date = date("date")
    val startTime = time("start_time")
    val endTime = time("end_time")
    val location = varchar("location", 128)
    val locationDetails = varchar("location_details", 128).nullable()
    val price = integer("price")
    val imageUrl = varchar("image_url", 256)

    override val primaryKey = PrimaryKey(id)
}
