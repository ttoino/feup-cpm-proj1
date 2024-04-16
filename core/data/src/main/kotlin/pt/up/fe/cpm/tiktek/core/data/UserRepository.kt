package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.User

interface UserRepository {
    fun getToken(): Flow<String?>

    fun getUser(): Flow<User?>

    suspend fun login(
        email: String,
        password: String,
    ): NetworkResult<Unit>

    suspend fun register(
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
        password: String,
        nameCc: String,
        numberCc: String,
        expirationDateCc: String,
        cvvCc: String,
    ): NetworkResult<Unit>

    suspend fun partialRegister(
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
    ): NetworkResult<Unit>

    suspend fun logout()
}
