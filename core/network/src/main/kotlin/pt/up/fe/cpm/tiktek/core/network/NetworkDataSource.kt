package pt.up.fe.cpm.tiktek.core.network

import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.AuthResponse
import pt.up.fe.cpm.tiktek.core.model.User

interface NetworkDataSource {
    // Auth
    suspend fun login(
        email: String,
        password: String,
    ): AuthResponse

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
    ): AuthResponse

    // Profile
    suspend fun getProfile(token: String): User

    suspend fun updateProfile(
        token: String,
        name: String,
        nif: String,
        birthdate: LocalDate,
        email: String,
        nameCc: String,
        numberCc: String,
        expirationDateCc: String,
        cvvCc: String,
    ): User

    suspend fun deleteProfile(token: String): Boolean
}
