package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.local.LocalUsersDataSource
import pt.up.fe.cpm.tiktek.core.model.User
import javax.inject.Inject

class RoomUsersDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalUsersDataSource {
        override suspend fun getUserByToken(token: String): User? = database.user.getByToken(token)?.toUser()

        override suspend fun insert(
            token: String,
            user: User,
        ) = database.user.insert(user.toEntity(token))

        override suspend fun deleteAll() = database.user.deleteAll()
    }

@Dao
internal interface UserDao {
    @Query("SELECT * FROM users WHERE token = :token")
    suspend fun getByToken(token: String): UserEntity?

    @Upsert
    suspend fun insert(user: UserEntity)

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}

@Entity(tableName = "users")
internal data class UserEntity(
    @PrimaryKey val token: String,
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
)

internal fun User.toEntity(token: String) =
    UserEntity(
        token = token,
        name = name,
        nif = nif,
        birthdate = birthdate,
        email = email,
        nameCc = nameCc,
        numberCc = numberCc,
        expirationDateCc = expirationDateCc,
        cvvCc = cvvCc,
    )

internal fun UserEntity.toUser() =
    User(
        name = name,
        nif = nif,
        birthdate = birthdate,
        email = email,
        nameCc = nameCc,
        numberCc = numberCc,
        expirationDateCc = expirationDateCc,
        cvvCc = cvvCc,
    )
