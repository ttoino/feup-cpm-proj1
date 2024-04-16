package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.date
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.update
import pt.up.fe.cpm.tiktek.core.database.UserDAO
import pt.up.fe.cpm.tiktek.core.model.UserWithPassword
import javax.inject.Inject

class ExposedUserDAO
    @Inject
    constructor(
        private val db: ExposedDatabaseConnection,
    ) : UserDAO {
        override suspend fun existsByEmail(email: String): Boolean =
            db.query {
                !Users.selectAll().where { Users.email eq email }.empty()
            }

        override suspend fun getByEmail(email: String): UserWithPassword? =
            db.query {
                Users.selectAll().where { Users.email eq email }.map { it.toUserWithPassword() }.firstOrNull()
            }

        override suspend fun create(user: UserWithPassword): UserWithPassword =
            db.query {
                Users.insert {
                    it.fromUserWithPassword(user)
                }
                user
            }

        override suspend fun update(user: UserWithPassword): UserWithPassword =
            db.query {
                Users.update({ Users.email eq user.email }) {
                    it.fromUserWithPassword(user)
                }
                user
            }

        override suspend fun delete(email: String): Boolean =
            db.query {
                Users.deleteWhere { Users.email eq email } > 0
            }
    }

private fun ResultRow.toUserWithPassword() =
    UserWithPassword(
        name = this[Users.name],
        nif = this[Users.nif],
        birthdate = this[Users.birthdate],
        email = this[Users.email],
        nameCc = this[Users.nameCc],
        numberCc = this[Users.numberCc],
        expirationDateCc = this[Users.expirationDateCc],
        cvvCc = this[Users.cvvCc],
        password = this[Users.password],
    )

private fun UpdateBuilder<*>.fromUserWithPassword(user: UserWithPassword) =
    apply {
        this[Users.name] = user.name
        this[Users.nif] = user.nif
        this[Users.birthdate] = user.birthdate
        this[Users.email] = user.email
        this[Users.nameCc] = user.nameCc
        this[Users.numberCc] = user.numberCc
        this[Users.expirationDateCc] = user.expirationDateCc
        this[Users.cvvCc] = user.cvvCc
        this[Users.password] = user.password
    }

internal object Users : Table() {
    val name = varchar("name", 255)
    val nif = char("nif", 9)
    val birthdate = date("birthdate")
    val email = varchar("email", 255)
    val nameCc = varchar("name_cc", 255)
    val numberCc = char("number_cc", 16)
    val expirationDateCc = char("expiration_date_cc", 5)
    val cvvCc = char("cvv_cc", 3)
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(email)
}
