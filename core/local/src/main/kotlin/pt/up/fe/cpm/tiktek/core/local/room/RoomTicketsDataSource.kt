package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import pt.up.fe.cpm.tiktek.core.local.LocalTicketsDataSource
import pt.up.fe.cpm.tiktek.core.model.Ticket
import javax.inject.Inject

class RoomTicketsDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalTicketsDataSource {
        override fun getTickets(): Flow<List<Ticket>> = database.ticket.getALl().map { it.map { it.toTicket() } }

        override fun getTicket(id: String): Flow<Ticket> = database.ticket.getById(id).map { it.toTicket() }

        override suspend fun insert(tickets: List<Ticket>) = database.ticket.insertAll(tickets.map { it.toEntity() })

        override suspend fun deleteTickets() = database.ticket.deleteAll()
    }

@Dao
internal interface TicketDao {
    @Query("SELECT * FROM tickets")
    fun getALl(): Flow<List<TicketEntity>>

    @Query("SELECT * FROM tickets WHERE id = :id")
    fun getById(id: String): Flow<TicketEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tickets: List<TicketEntity>)

    @Query("DELETE FROM tickets")
    suspend fun deleteAll()
}

@Entity(tableName = "tickets")
internal data class TicketEntity(
    @PrimaryKey
    val id: String,
    val eventId: String,
    val userId: String,
    val seat: String,
    val purchaseDate: Instant,
    val useDate: Instant?,
)

internal fun Ticket.toEntity() =
    TicketEntity(
        id = id,
        eventId = eventId,
        userId = userId,
        seat = seat,
        purchaseDate = purchaseDate,
        useDate = useDate,
    )

internal fun TicketEntity.toTicket() =
    Ticket(
        id = id,
        eventId = eventId,
        userId = userId,
        seat = seat,
        purchaseDate = purchaseDate,
        useDate = useDate,
    )
