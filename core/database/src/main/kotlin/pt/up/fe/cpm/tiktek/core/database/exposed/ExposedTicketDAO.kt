package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
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
        override suspend fun getAllByUser(userEmail: String): List<Ticket> =
            db.query {
                Tickets.selectAll().where { Tickets.user eq userEmail }.map {
                    it.toTicket()
                }
            }

        override suspend fun getById(id: String): Ticket? =
            db.query {
                Tickets.selectAll().where { Tickets.id eq id }.map {
                    it.toTicket()
                }.firstOrNull()
            }

        override suspend fun create(ticket: Ticket): Ticket =
            db.query {
                Tickets.insert {
                    it.fromTicket(ticket)
                }
                ticket
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
        userEmail = this[Tickets.user],
        seat = this[Tickets.seat],
        purchaseDate = this[Tickets.purchaseDate],
    )

private fun UpdateBuilder<*>.fromTicket(ticket: Ticket) {
    this[Tickets.id] = ticket.id
    this[Tickets.event] = ticket.eventId
    this[Tickets.user] = ticket.userEmail
    this[Tickets.seat] = ticket.seat
    this[Tickets.purchaseDate] = ticket.purchaseDate
}

internal object Tickets : Table() {
    val id = varchar("id", 128)
    val event = reference("event", Events.id)
    val user = reference("user", Users.email)
    val seat = varchar("seat", 50)
    val purchaseDate = timestamp("purchase_date")

    override val primaryKey = PrimaryKey(id)

    init {
        uniqueIndex(event, seat)
    }
}
