package pt.up.fe.cpm.tiktek.core.model.validation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Violation {
    @SerialName("Unknown error")
    UNKNOWN,

    @SerialName("You must be at least 13 years old")
    BIRTHDATE,

    @SerialName("Must be a 3 digit number")
    CVC_CC,

    @SerialName("Must be a valid email")
    EMAIL,

    @SerialName("This email already has a registered account")
    EMAIL_ALREADY_EXISTS,

    @SerialName("Must be in the format mm/yy")
    EXPIRATION_DATE_CC,

    @SerialName("Must be a 9 digit number")
    NIF,

    @SerialName("Not a valid tax number")
    NIF_CHECK,

    @SerialName("Must be a 16 digit number")
    NUMBER_CC,

    @SerialName("Not a valid credit card number")
    NUMBER_CC_CHECK,

    @SerialName("Must be at least 8 characters")
    PASSWORD,

    @SerialName("Required")
    REQUIRED,

    @SerialName("Account with these credentials not found")
    LOGIN,
}
