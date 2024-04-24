package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Instant
import pt.up.fe.cpm.tiktek.core.local.LocalOrdersDataSource
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderItem
import javax.inject.Inject

class RoomOrdersDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalOrdersDataSource {
        override fun getOrders(): Flow<List<Order>> = database.order.getALl().map { it.map { it.toOrder() } }

        override suspend fun insert(orders: List<Order>) {
            database.order.insertOrders(orders.map { it.toEntity() })
            database.order.insertOrderItems(orders.flatMap { order -> order.items.map { it.toEntity(order.id) } })
        }

        override suspend fun deleteOrders() {
            database.order.deleteOrders()
            database.order.deleteOrderItems()
        }
    }

@Dao
internal interface OrderDao {
    @Transaction
    @Query("SELECT * FROM orders")
    fun getALl(): Flow<List<OrderWithItemsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrders(orders: List<OrderEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderItems(items: List<OrderItemEntity>)

    @Query("DELETE FROM orders")
    suspend fun deleteOrders()

    @Query("DELETE FROM order_items")
    suspend fun deleteOrderItems()
}

@Entity(tableName = "orders")
internal data class OrderEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    val date: Instant,
)

@Entity(
    tableName = "order_items",
    primaryKeys = ["order_id", "item_id"],
)
internal data class OrderItemEntity(
    @ColumnInfo(name = "order_id")
    val orderId: String,
    @ColumnInfo(name = "item_id")
    val itemId: String,
    val quantity: Int,
)

internal data class OrderWithItemsEntity(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "order_id",
    )
    val items: List<OrderItemEntity>,
)

internal fun Order.toEntity() =
    OrderEntity(
        id = id,
        userId = userId,
        date = date,
    )

internal fun OrderItem.toEntity(orderId: String) =
    OrderItemEntity(
        orderId = orderId,
        itemId = itemId,
        quantity = quantity,
    )

internal fun OrderWithItemsEntity.toOrder() =
    Order(
        id = order.id,
        userId = order.userId,
        date = order.date,
        items =
            items.map { item ->
                OrderItem(
                    itemId = item.itemId,
                    quantity = item.quantity,
                )
            },
    )
