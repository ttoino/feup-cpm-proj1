package pt.up.fe.cpm.tiktek.backend

import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import pt.up.fe.cpm.tiktek.core.model.ErrorResponse
import pt.up.fe.cpm.tiktek.core.model.validation.Violation

class RequestViolationException(
    val value: Any,
    val violation: Violation,
    val statusCode: HttpStatusCode? = null,
) : IllegalStateException()

fun Application.errorModule() {
    install(StatusPages) {
        status(*HttpStatusCode.allStatusCodes.filter { !it.isSuccess() }.toTypedArray()) { call, status ->
            call.respond<ErrorResponse>(
                status,
                ErrorResponse.Unknown(
                    status.value,
                    status.description,
                ),
            )
        }
        exception<RequestViolationException> { call, cause ->
            val status = cause.statusCode ?: HttpStatusCode.BadRequest
            call.respond<ErrorResponse>(
                status,
                ErrorResponse.GeneralViolation(
                    status.value,
                    status.description,
                    cause.violation,
                ),
            )
        }
        exception<RequestValidationException> { call, cause ->
            val status = HttpStatusCode.UnprocessableEntity
            call.respond<ErrorResponse>(
                status,
                ErrorResponse.FieldValidation(
                    status.value,
                    status.description,
                    cause.reasons.associate {
                        it.split(",").let { it[0] to Violation.valueOf(it[1]) }
                    },
                ),
            )
        }
    }
}
