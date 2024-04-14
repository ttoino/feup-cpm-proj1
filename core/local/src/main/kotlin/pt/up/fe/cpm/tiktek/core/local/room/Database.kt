package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [
        CafeteriaItemEntity::class,
        EventEntity::class,
    ],
    version = 1,
)
@TypeConverters(Converters::class)
internal abstract class Database : RoomDatabase() {
    abstract val cafeteriaItem: CafeteriaItemDao
    abstract val event: EventDao
}
