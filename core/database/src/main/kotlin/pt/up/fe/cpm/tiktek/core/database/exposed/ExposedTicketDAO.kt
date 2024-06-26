package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.TicketDAO
import pt.up.fe.cpm.tiktek.core.model.Ticket
import javax.inject.Inject

class ExposedTicketDAO
    @Inject
    constructor(private val db: ExposedDatabaseConnection) : TicketDAO {
        override suspend fun getAllByUser(userId: String): List<Ticket> =
            db.query {
                Tickets.selectAll().where { Tickets.user eq userId }.map {
                    it.toTicket()
                }
            }

        override suspend fun getById(id: String): Ticket? =
            db.query {
                Tickets.selectAll().where { Tickets.id eq id }.map {
                    it.toTicket()
                }.firstOrNull()
            }

        override suspend fun getLastSeatByEventId(eventId: String): String? =
            db.query {
                Tickets.select(Tickets.seat).where {
                    Tickets.event eq eventId
                }.orderBy(Tickets.seat, SortOrder.DESC_NULLS_LAST).map { it[Tickets.seat] }.firstOrNull()
            }

        override suspend fun create(ticket: Ticket): Ticket =
            db.query {
                Tickets.insert {
                    it.fromTicket(ticket)
                }
                ticket
            }

        override suspend fun createAll(tickets: List<Ticket>): List<Ticket> =
            db.query {
                Tickets.batchInsert(tickets) {
                    fromTicket(it)
                }
                tickets
            }

        override suspend fun update(ticket: Ticket): Ticket =
            db.query {
                Tickets.update({ Tickets.id eq ticket.id }) {
                    it.fromTicket(ticket)
                }
                ticket
            }

        override suspend fun delete(id: String): Boolean =
            db.query {
                Tickets.deleteWhere { Tickets.id eq id } > 0
            }
    }

private fun ResultRow.toTicket() =
    Ticket(
        id = this[Tickets.id],
        eventId = this[Tickets.event],
        userId = this[Tickets.user],
        seat = this[Tickets.seat],
        purchaseDate = this[Tickets.purchaseDate],
        useDate = this[Tickets.useDate],
    )

private fun UpdateBuilder<*>.fromTicket(ticket: Ticket) {
    this[Tickets.id] = ticket.id
    this[Tickets.event] = ticket.eventId
    this[Tickets.user] = ticket.userId
    this[Tickets.seat] = ticket.seat
    this[Tickets.purchaseDate] = ticket.purchaseDate
    this[Tickets.useDate] = ticket.useDate
}

internal object Tickets : Table() {
    val id = char("id", UUID_LENGTH)
    val event = reference("event", Events.id)
    val user = reference("user", Users.id)
    val seat = varchar("seat", SHORT_LENGTH)
    val purchaseDate = timestamp("purchase_date")
    val useDate = timestamp("use_date").nullable()

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(event, seat)
    }
}
