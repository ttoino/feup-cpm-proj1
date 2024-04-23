package pt.up.fe.cpm.tiktek.core.data.localfirst

import android.content.Context
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.work.Deletable
import pt.up.fe.cpm.tiktek.core.data.work.Syncable
import pt.up.fe.cpm.tiktek.core.data.work.enqueueDelete
import pt.up.fe.cpm.tiktek.core.data.work.enqueueSync
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalUserDataSource
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.User
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class LocalFirstUserRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
        private val networkDataSource: NetworkDataSource,
        private val localDataSource: LocalUserDataSource,
        private val authenticationTokenDataSource: LocalAuthenticationTokenDataSource,
    ) : UserRepository, Syncable, Deletable {
        override suspend fun sync(): NetworkResult<Unit> {
            val token = getToken().first() ?: return NetworkResult.Failure

            val result = networkDataSource.getProfile(token)

            result.getOrNull()?.let {
                localDataSource.update(it)
            }

            return result.map {}
        }

        override suspend fun delete() {
            localDataSource.delete()
        }

        override fun getToken(): Flow<String?> = authenticationTokenDataSource.token()

        override fun getUser(): Flow<User?> = localDataSource.user()

        private suspend fun handleToken(token: String) {
            WorkManager.getInstance(context).enqueueSync()
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
            WorkManager.getInstance(context).enqueueDelete()
            authenticationTokenDataSource.setToken(null)
        }

        override suspend fun updatePersonalInformation(
            name: String,
            nif: String,
            birthdate: LocalDate,
            email: String,
        ): NetworkResult<Unit> {
            val token = getToken().first() ?: return NetworkResult.Failure
            val user = getUser().first() ?: return NetworkResult.Failure

            val result =
                networkDataSource.updateProfile(
                    token,
                    id = user.id,
                    name,
                    nif,
                    birthdate,
                    email,
                    nameCc = user.nameCc,
                    numberCc = user.numberCc,
                    expirationDateCc = user.expirationDateCc,
                    cvvCc = user.cvvCc,
                )

            result.getOrNull()?.let {
                localDataSource.update(it)
            }

            return result.map { }
        }

        override suspend fun updatePaymentInformation(
            nameCc: String,
            numberCc: String,
            expirationDateCc: String,
            cvvCc: String,
        ): NetworkResult<Unit> {
            val token = getToken().first() ?: return NetworkResult.Failure
            val user = getUser().first() ?: return NetworkResult.Failure

            val result =
                networkDataSource.updateProfile(
                    token,
                    id = user.id,
                    name = user.name,
                    nif = user.nif,
                    birthdate = user.birthdate,
                    email = user.email,
                    nameCc,
                    numberCc,
                    expirationDateCc,
                    cvvCc,
                )

            result.getOrNull()?.let {
                localDataSource.update(it)
            }

            return result.map {}
        }
    }
