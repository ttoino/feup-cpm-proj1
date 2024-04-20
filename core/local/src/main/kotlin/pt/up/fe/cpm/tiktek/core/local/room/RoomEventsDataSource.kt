package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import pt.up.fe.cpm.tiktek.core.local.LocalEventsDataSource
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject

class RoomEventsDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalEventsDataSource {
        override fun getEvents(): Flow<List<Event>> = database.event.getAll().map { it.map { it.toEvent() } }

        override fun getEvent(id: String): Flow<Event> = database.event.getById(id).map { it.toEvent() }

        override suspend fun insert(events: List<Event>) = database.event.insertAll(events.map { it.toEntity() })
    }

@Dao
internal interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<EventEntity>>

    @Query("SELECT * FROM events WHERE id = :id")
    fun getById(id: String): Flow<EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun deleteAll()
}

@Entity(tableName = "events")
internal data class EventEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val date: LocalDate,
    @ColumnInfo(name = "start_time")
    val startTime: LocalTime,
    @ColumnInfo(name = "end_time")
    val endTime: LocalTime,
    val location: String,
    @ColumnInfo(name = "location_details")
    val locationDetails: String?,
    val price: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)

internal fun Event.toEntity() =
    EventEntity(
        id = id,
        name = name,
        description = description,
        date = date,
        startTime = startTime,
        endTime = endTime,
        location = location,
        locationDetails = locationDetails,
        price = price,
        imageUrl = imageUrl,
    )

internal fun EventEntity.toEvent() =
    Event(
        id = id,
        name = name,
        description = description,
        date = date,
        startTime = startTime,
        endTime = endTime,
        location = location,
        locationDetails = locationDetails,
        price = price,
        imageUrl = imageUrl,
    )
