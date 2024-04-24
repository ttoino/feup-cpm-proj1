package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.OrderDAO
import pt.up.fe.cpm.tiktek.core.model.Order
import pt.up.fe.cpm.tiktek.core.model.OrderItem
import javax.inject.Inject

class ExposedOrderDAO
    @Inject
    constructor(private val db: ExposedDatabaseConnection) : OrderDAO {
        override suspend fun getAllByUser(userId: String): List<Order> =
            db.query {
                Orders.selectAll().where { Orders.user eq userId }.map {
                    it.toOrder(
                        OrderItems.selectAll().where { OrderItems.order eq it[Orders.id] }.map { item -> item.toOrderItem() },
                    )
                }
            }

        override suspend fun getById(id: String): Order? =
            db.query {
                Orders.selectAll().where { Orders.id eq id }.map {
                    it.toOrder(
                        OrderItems.selectAll().where { OrderItems.order eq it[Orders.id] }.map { item -> item.toOrderItem() },
                    )
                }.firstOrNull()
            }

        override suspend fun create(order: Order): Order =
            db.query {
                Orders.insert {
                    it.fromOrder(order)
                }
                OrderItems.batchInsert(order.items) {
                    this.fromOrderItem(it, order.id)
                }
                order
            }

        override suspend fun update(order: Order): Order =
            db.query {
                Orders.update({ Orders.id eq order.id }) {
                    it.fromOrder(order)
                }
                OrderItems.deleteWhere { OrderItems.order eq order.id }
                OrderItems.batchInsert(order.items) {
                    this.fromOrderItem(it, order.id)
                }
                order
            }

        override suspend fun delete(id: String): Boolean =
            db.query {
                Orders.deleteWhere { Orders.id eq id } > 0 &&
                    OrderItems.deleteWhere { order eq id } > 0
            }
    }

private fun ResultRow.toOrder(items: List<OrderItem>) =
    Order(
        id = this[Orders.id],
        userId = this[Orders.user],
        items = items,
        date = this[Orders.date],
    )

private fun ResultRow.toOrderItem() =
    OrderItem(
        itemId = this[OrderItems.item],
        quantity = this[OrderItems.quantity],
    )

private fun UpdateBuilder<*>.fromOrder(order: Order) =
    apply {
        this[Orders.id] = order.id
        this[Orders.user] = order.userId
        this[Orders.date] = order.date
    }

private fun UpdateBuilder<*>.fromOrderItem(
    orderItem: OrderItem,
    orderId: String,
) = apply {
    this[OrderItems.order] = orderId
    this[OrderItems.item] = orderItem.itemId
    this[OrderItems.quantity] = orderItem.quantity
}

internal object Orders : Table() {
    val id = char("id", UUID_LENGTH)
    val user = reference("user", Users.id)
    val date = timestamp("date")

    override val primaryKey = PrimaryKey(id)
}

internal object OrderItems : Table() {
    val order = reference("order", Orders.id)
    val item = reference("item", CafeteriaItems.id)
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(order, item)
}
