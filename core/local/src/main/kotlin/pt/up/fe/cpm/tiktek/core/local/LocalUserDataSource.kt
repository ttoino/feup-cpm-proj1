package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.User

interface LocalUserDataSource {
    fun user(): Flow<User?>

    suspend fun update(user: User)

    suspend fun delete()
}
