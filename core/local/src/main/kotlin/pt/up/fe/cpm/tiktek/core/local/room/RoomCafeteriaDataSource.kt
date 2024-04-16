package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import javax.inject.Inject

internal class RoomCafeteriaDataSource
    @Inject
    constructor(
        private val database: Database,
    ) {
        fun getCafeteriaItems(): List<CafeteriaItem> = database.cafeteriaItem.getAll().map { it.toCafeteriaItem() }
    }

@Dao
internal interface CafeteriaItemDao {
    @Query("SELECT * FROM cafeteria_items")
    fun getAll(): List<CafeteriaItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(items: List<CafeteriaItemEntity>)

    @Query("DELETE FROM cafeteria_items")
    fun deleteAll()
}

@Entity(
    tableName = "cafeteria_items",
)
internal data class CafeteriaItemEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val price: Int,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
)

internal fun CafeteriaItemEntity.toCafeteriaItem() =
    CafeteriaItem(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
