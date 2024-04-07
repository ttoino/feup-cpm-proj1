package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.VoucherDAO
import pt.up.fe.cpm.tiktek.core.model.Voucher
import javax.inject.Inject

class ExposedVoucherDAO
    @Inject
    constructor(private val db: ExposedDatabaseConnection) : VoucherDAO {
        override suspend fun getAllByUser(userEmail: String): List<Voucher> =
            db.query {
                Vouchers.selectAll().where { Vouchers.user eq userEmail }.map { it.toVoucher() }
            }

        override suspend fun getById(id: String): Voucher? =
            db.query {
                Vouchers.selectAll().where { Vouchers.id eq id }.map { it.toVoucher() }.firstOrNull()
            }

        override suspend fun create(voucher: Voucher): Voucher =
            db.query {
                Vouchers.insert {
                    it.fromVoucher(voucher)
                }
                voucher
            }

        override suspend fun update(voucher: Voucher): Voucher =
            db.query {
                Vouchers.update({ Vouchers.id eq voucher.id }) {
                    it.fromVoucher(voucher)
                }
                voucher
            }

        override suspend fun delete(id: String): Boolean =
            db.query {
                Vouchers.deleteWhere { Vouchers.id eq id } > 0
            }
    }

private fun ResultRow.toVoucher(): Voucher =
    this[Vouchers.discount]?.let {
        Voucher.Discount(
            id = this[Vouchers.id],
            discount = it,
            userEmail = this[Vouchers.user],
            orderId = this[Vouchers.order],
        )
    } ?: this[Vouchers.item]?.let {
        Voucher.Free(
            id = this[Vouchers.id],
            itemId = it,
            userEmail = this[Vouchers.user],
            orderId = this[Vouchers.order],
        )
    } ?: throw IllegalStateException("Voucher has neither discount nor item")

private fun UpdateBuilder<*>.fromVoucher(voucher: Voucher) =
    apply {
        this[Vouchers.id] = voucher.id
        this[Vouchers.user] = voucher.userEmail
        this[Vouchers.order] = voucher.orderId

        when (voucher) {
            is Voucher.Discount -> {
                this[Vouchers.discount] = voucher.discount
                this[Vouchers.item] = null
            }
            is Voucher.Free -> {
                this[Vouchers.discount] = null
                this[Vouchers.item] = voucher.itemId
            }
        }
    }

internal object Vouchers : Table() {
    val id = varchar("id", 128)
    val discount = integer("discount").nullable()
    val item = reference("item", CafeteriaItems.id).nullable()
    val user = reference("user", Users.email)
    val order = reference("order", Orders.id).nullable()

    override val primaryKey = PrimaryKey(id)
}
