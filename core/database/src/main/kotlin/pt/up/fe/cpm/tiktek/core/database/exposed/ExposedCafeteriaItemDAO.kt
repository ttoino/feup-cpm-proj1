package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.CafeteriaItemDAO
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import javax.inject.Inject

class ExposedCafeteriaItemDAO
    @Inject
    constructor(
        private val db: ExposedDatabaseConnection,
    ) : CafeteriaItemDAO {
        override suspend fun getAll(): List<CafeteriaItem> =
            db.query {
                CafeteriaItems.selectAll().map { it.toCafeteriaItem() }
            }

        override suspend fun getById(id: String): CafeteriaItem? =
            db.query {
                CafeteriaItems.selectAll().where { CafeteriaItems.id eq id }.map { it.toCafeteriaItem() }.firstOrNull()
            }

        override suspend fun create(cafeteriaItem: CafeteriaItem): CafeteriaItem =
            db.query {
                CafeteriaItems.insert {
                    it.fromCafeteriaItem(cafeteriaItem)
                }
                cafeteriaItem
            }

        override suspend fun createAll(cafeteriaItems: List<CafeteriaItem>): List<CafeteriaItem> =
            db.query {
                CafeteriaItems.batchInsert(cafeteriaItems) {
                    fromCafeteriaItem(it)
                }
                cafeteriaItems
            }

        override suspend fun update(cafeteriaItem: CafeteriaItem): CafeteriaItem =
            db.query {
                CafeteriaItems.update({ CafeteriaItems.id eq cafeteriaItem.id }) {
                    it.fromCafeteriaItem(cafeteriaItem)
                }
                cafeteriaItem
            }

        override suspend fun delete(id: String): Boolean =
            db.query {
                CafeteriaItems.deleteWhere { CafeteriaItems.id eq id } > 0
            }
    }

private fun ResultRow.toCafeteriaItem() =
    CafeteriaItem(
        id = this[CafeteriaItems.id],
        name = this[CafeteriaItems.name],
        price = this[CafeteriaItems.price],
        imageUrl = this[CafeteriaItems.imageUrl],
    )

private fun UpdateBuilder<*>.fromCafeteriaItem(cafeteriaItem: CafeteriaItem) =
    apply {
        this[CafeteriaItems.id] = cafeteriaItem.id
        this[CafeteriaItems.name] = cafeteriaItem.name
        this[CafeteriaItems.price] = cafeteriaItem.price
        this[CafeteriaItems.imageUrl] = cafeteriaItem.imageUrl
    }

internal object CafeteriaItems : Table() {
    val id = varchar("id", 128)
    val name = varchar("name", 255)
    val price = integer("price")
    val imageUrl = varchar("image_url", 255)

    override val primaryKey = PrimaryKey(id)
}
