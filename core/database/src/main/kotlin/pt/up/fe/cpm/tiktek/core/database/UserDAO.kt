package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.UserWithPassword

interface UserDAO {
    suspend fun existsByEmail(email: String): Boolean

    suspend fun getByEmail(email: String): UserWithPassword?

    suspend fun create(user: UserWithPassword): UserWithPassword

    suspend fun update(user: UserWithPassword): UserWithPassword

    suspend fun delete(email: String): Boolean
}
