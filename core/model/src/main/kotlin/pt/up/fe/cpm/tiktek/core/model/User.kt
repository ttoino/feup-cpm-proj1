package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Validate
@Serializable
data class User(
    val id: String,
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
) {
    fun withPassword(password: String) =
        UserWithPassword(
            id = id,
            name = name,
            nif = nif,
            birthdate = birthdate,
            email = email,
            nameCc = nameCc,
            numberCc = numberCc,
            expirationDateCc = expirationDateCc,
            cvvCc = cvvCc,
            password = password,
        )
}

@Validate
@Serializable
data class UserWithPassword(
    val id: String,
    val name: String,
    val nif: String,
    val birthdate: LocalDate,
    val email: String,
    val nameCc: String,
    val numberCc: String,
    val expirationDateCc: String,
    val cvvCc: String,
    val password: String,
) {
    fun withoutPassword() =
        User(
            id = id,
            name = name,
            nif = nif,
            birthdate = birthdate,
            email = email,
            nameCc = nameCc,
            numberCc = numberCc,
            expirationDateCc = expirationDateCc,
            cvvCc = cvvCc,
        )
}
