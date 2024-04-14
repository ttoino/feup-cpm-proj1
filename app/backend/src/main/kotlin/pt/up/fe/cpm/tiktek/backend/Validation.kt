package pt.up.fe.cpm.tiktek.backend

import dev.nesk.akkurate.Validator
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import pt.up.fe.cpm.tiktek.core.model.validation.loginRequestValidator
import pt.up.fe.cpm.tiktek.core.model.validation.registerRequestValidator
import pt.up.fe.cpm.tiktek.core.model.validation.userValidator
import dev.nesk.akkurate.ValidationResult as AkkurateResult

fun Application.validationModule() {
    install(RequestValidation) {
        validator(loginRequestValidator)
        validator(registerRequestValidator)
        validator(userValidator)
    }
}

inline fun <reified T : Any> RequestValidationConfig.validator(validator: Validator.Runner<T>) {
    validate<T> {
        when (val result = validator(it)) {
            is AkkurateResult.Success -> ValidationResult.Valid
            is AkkurateResult.Failure ->
                ValidationResult.Invalid(
                    result.violations.map {
                        println(it.path)
                        val path = it.path.joinToString(".")
                        if (path.isNotBlank()) "$path,${it.message}" else it.message
                    },
                )
        }
    }
}
