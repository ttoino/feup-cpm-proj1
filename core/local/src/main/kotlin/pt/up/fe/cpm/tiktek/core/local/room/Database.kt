package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CafeteriaItemEntity::class,
        EventEntity::class,
        TicketEntity::class,
        UserEntity::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
    internal abstract val cafeteriaItem: CafeteriaItemDao
    internal abstract val event: EventDao
    internal abstract val ticket: TicketDao
    internal abstract val user: UserDao
}
