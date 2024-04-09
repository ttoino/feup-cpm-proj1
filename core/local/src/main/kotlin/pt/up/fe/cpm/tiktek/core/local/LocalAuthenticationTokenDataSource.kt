package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow

interface LocalAuthenticationTokenDataSource {
    fun token(): Flow<String?>

    suspend fun setToken(token: String?)
}
