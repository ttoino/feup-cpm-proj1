package pt.up.fe.cpm.tiktek.core.local

import pt.up.fe.cpm.tiktek.core.model.User

interface LocalUsersDataSource {
    suspend fun getUserByToken(token: String): User?

    suspend fun insert(
        token: String,
        user: User,
    )

    suspend fun deleteAll()
}
