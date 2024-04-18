package pt.up.fe.cpm.tiktek.core.data.localfirst

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalUsersDataSource
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstUserRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalUsersDataSource,
        private val authenticationTokenDataSource: LocalAuthenticationTokenDataSource,
    ) : UserRepository {
        override fun getToken(): Flow<String?> = authenticationTokenDataSource.token()

        override fun getUser(): Flow<User?> =
            authenticationTokenDataSource.token().map {
                it?.let {
                    localDataSource.getUserByToken(it)
                        ?: networkDataSource.getProfile(it).getOrNull()
                }
            }

        private suspend fun handleToken(token: String) {
            networkDataSource.getProfile(token).getOrNull()?.let {
                localDataSource.insert(token, it)
            }
            // This needs to be last, because it causes a navigation that kills this repository instance
            authenticationTokenDataSource.setToken(token)
        }

        override suspend fun login(
            email: String,
            password: String,
        ): NetworkResult<Unit> {
            val result = networkDataSource.login(email, password)

            result.getOrNull()?.let { handleToken(it.token) }

            return result.map { }
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
        ): NetworkResult<Unit> {
            val result =
                networkDataSource.register(
                    name,
                    nif,
                    birthdate,
                    email,
                    password,
                    nameCc,
                    numberCc,
                    expirationDateCc,
                    cvvCc,
                )

            result.getOrNull()?.let { handleToken(it.token) }

            return result.map { }
        }

        override suspend fun partialRegister(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
        ): NetworkResult<Unit> = networkDataSource.partialRegister(name, nif, birthdate, email)

        override suspend fun logout() {
            authenticationTokenDataSource.setToken(null)
            localDataSource.deleteAll()
        }

        override suspend fun editPersonalInfo(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
            password: String,
        ): NetworkResult<Unit> {
            val token = getToken().first() ?: return NetworkResult.Failure
            val user = getUser().first() ?: return NetworkResult.Failure

            val result =
                networkDataSource.updateProfile(
                    token,
                    name,
                    nif,
                    birthdate,
                    email,
                    nameCc = user.nameCc,
                    numberCc = user.numberCc,
                    expirationDateCc = user.expirationDateCc,
                    cvvCc = user.cvvCc,
                    password,
                )

            result.getOrNull()?.let {
                localDataSource.insert(token, it)
            }

            return result.map {
            }
        }

        override suspend fun editPassword(password: String): NetworkResult<Unit> {
            TODO("Not yet implemented")
        }

        override suspend fun editCreditCard(
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
            password: String,
        ): NetworkResult<Unit> {
            val token = getToken().first() ?: return NetworkResult.Failure
            val user = getUser().first() ?: return NetworkResult.Failure

            val result =
                networkDataSource.updateProfile(
                    token,
                    name = user.name,
                    nif = user.nif,
                    birthdate = user.birthdate,
                    email = user.email,
                    nameCc,
                    numberCc,
                    expirationDateCc,
                    cvvCc,
                    password,
                )

            result.getOrNull()?.let {
                localDataSource.insert(token, it)
            }

            return result.map {
            }
        }
    }
