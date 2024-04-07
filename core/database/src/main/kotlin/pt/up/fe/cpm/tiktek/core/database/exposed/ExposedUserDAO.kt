package pt.up.fe.cpm.tiktek.core.database.exposed

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
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
        email = this[Users.email],
        password = this[Users.password],
    )

private fun UpdateBuilder<*>.fromUserWithPassword(user: UserWithPassword) =
    apply {
        this[Users.email] = user.email
        this[Users.password] = user.password
        this[Users.name] = user.name
    }

internal object Users : Table() {
    val email = varchar("email", 255)
    val password = varchar("password", 255)
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(email)
}
