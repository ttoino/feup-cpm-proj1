package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.local.LocalCafeteriaDataSource
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import javax.inject.Inject

class RoomCafeteriaDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalCafeteriaDataSource {
        override fun getCafeteriaItems(): Flow<List<CafeteriaItem>> =
            database.cafeteriaItem.getAll().map { it.map { it.toCafeteriaItem() } }

        override suspend fun insert(items: List<CafeteriaItem>) = database.cafeteriaItem.insertAll(items.map { it.toEntity() })
    }

@Dao
internal interface CafeteriaItemDao {
    @Query("SELECT * FROM cafeteria_items")
    fun getAll(): Flow<List<CafeteriaItemEntity>>

    @Upsert
    suspend fun insertAll(items: List<CafeteriaItemEntity>)

    @Query("DELETE FROM cafeteria_items")
    suspend fun deleteAll()
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

internal fun CafeteriaItem.toEntity() =
    CafeteriaItemEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )

internal fun CafeteriaItemEntity.toCafeteriaItem() =
    CafeteriaItem(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
