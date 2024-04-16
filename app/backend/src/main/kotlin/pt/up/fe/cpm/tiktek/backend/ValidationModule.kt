package pt.up.fe.cpm.tiktek.backend

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.validateWith
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult
import pt.up.fe.cpm.tiktek.backend.di.database
import pt.up.fe.cpm.tiktek.core.model.PartialRegisterRequest
import pt.up.fe.cpm.tiktek.core.model.RegisterRequest
import pt.up.fe.cpm.tiktek.core.model.validation.SuspendableValidator
import pt.up.fe.cpm.tiktek.core.model.validation.Violation
import pt.up.fe.cpm.tiktek.core.model.validation.accessors.email
import pt.up.fe.cpm.tiktek.core.model.validation.loginRequestValidator
import pt.up.fe.cpm.tiktek.core.model.validation.otherwise
import pt.up.fe.cpm.tiktek.core.model.validation.partialRegisterRequestValidator
import pt.up.fe.cpm.tiktek.core.model.validation.registerRequestValidator
import pt.up.fe.cpm.tiktek.core.model.validation.userValidator
import dev.nesk.akkurate.ValidationResult as AkkurateResult

private val Application.serverEmailValidator get() =
    SuspendableValidator<String> {
        constrain {
            !database.user.existsByEmail(it)
        } otherwise Violation.EMAIL_ALREADY_EXISTS
    }

private val Application.serverRegisterRequestValidator get() =
    SuspendableValidator<RegisterRequest> {
        validateWith(registerRequestValidator)
        email.validateWith(serverEmailValidator)
    }

private val Application.serverPartialRegisterRequestValidator get() =
    SuspendableValidator<PartialRegisterRequest> {
        validateWith(partialRegisterRequestValidator)
        email.validateWith(serverEmailValidator)
    }

fun Application.validationModule() {
    install(RequestValidation) {
        validator(loginRequestValidator)
        validator(serverRegisterRequestValidator)
        validator(serverPartialRegisterRequestValidator)
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

inline fun <reified T : Any> RequestValidationConfig.validator(validator: Validator.SuspendableRunner<T>) {
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
