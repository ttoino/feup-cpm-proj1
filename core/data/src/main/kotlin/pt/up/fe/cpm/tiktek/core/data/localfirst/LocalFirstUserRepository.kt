package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import timber.log.Timber
import javax.inject.Inject

class LocalFirstUserRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val authenticationTokenDataSource: LocalAuthenticationTokenDataSource,
    ) : UserRepository {
        override fun getToken(): Flow<String?> = authenticationTokenDataSource.token()

        override fun getUser(): Flow<User?> =
            authenticationTokenDataSource.token().map {
                try {
                    it?.let { networkDataSource.getProfile(it) }
                } catch (e: Throwable) {
                    Timber.w(e)
                    null
                }
            }

        override suspend fun login(
            email: String,
            password: String,
        ): Boolean {
            try {
                val response = networkDataSource.login(email, password)
                authenticationTokenDataSource.setToken(response.token)
                return true
            } catch (e: Exception) {
                Timber.w(e)
                return false
            }
        }

        override suspend fun register(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
            password: String,
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
        ): Boolean {
            try {
                val response = networkDataSource.register(name, nif, birthdate, email, password, nameCc, numberCc, expirationDateCc, cvvCc)
                authenticationTokenDataSource.setToken(response.token)
                return true
            } catch (e: Exception) {
                Timber.w(e)
                return false
            }
        }

        override suspend fun logout() {
            authenticationTokenDataSource.setToken(null)
        }
    }
