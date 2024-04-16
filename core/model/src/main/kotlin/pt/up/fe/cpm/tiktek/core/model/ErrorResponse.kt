package pt.up.fe.cpm.tiktek.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import pt.up.fe.cpm.tiktek.core.model.validation.Violation

@Serializable
sealed class ErrorResponse {
    abstract val status: Int
    abstract val title: String

    @Serializable
    @SerialName("unknown")
    data class Unknown(
        override val status: Int,
        override val title: String,
    ) : ErrorResponse()

    @Serializable
    @SerialName("violation")
    data class GeneralViolation(
        override val status: Int,
        override val title: String,
        val violation: Violation,
    ) : ErrorResponse()

    @Serializable
    @SerialName("violations")
    data class FieldValidation(
        override val status: Int,
        override val title: String,
        val violations: Map<String, Violation>,
    ) : ErrorResponse()
}
