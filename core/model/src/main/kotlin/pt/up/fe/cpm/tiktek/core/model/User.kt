package pt.up.fe.cpm.tiktek.core.model

import dev.nesk.akkurate.annotations.Validate
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Validate
@Serializable
data class User(
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
