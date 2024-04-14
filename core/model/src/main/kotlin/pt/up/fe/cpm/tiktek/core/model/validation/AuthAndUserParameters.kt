package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.hasLengthEqualTo
import dev.nesk.akkurate.constraints.builders.hasLengthGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isMatching
import dev.nesk.akkurate.constraints.builders.isNotBlank
import dev.nesk.akkurate.constraints.builders.isNotNull
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.toLocalDateTime

val nameValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
    }

val nifValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        hasLengthEqualTo(9) otherwise Violation.NIF
        isAllDigits() otherwise Violation.NIF
        constrain {
            // Validate the check digit (mod 11 algorithm)
            val total = it.take(8).reversed().withIndex().sumOf { c -> (c.value - '0') * (c.index + 2) }
            val checkDigit = '0' + 11 - total % 11
            it.endsWith(checkDigit)
        } otherwise Violation.NIF_CHECK
    }

val birthdateValidator =
    Validator<LocalDate?> {
        isNotNull() otherwise Violation.REQUIRED
        constrainIfNotNull {
            val now = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            it.periodUntil(now).years >= 13
        } otherwise Violation.BIRTHDATE
    }

val emailRegex =
    Regex("""^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$""")
val emailValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        isMatching(emailRegex) otherwise Violation.EMAIL
    }

val passwordValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        hasLengthGreaterThanOrEqualTo(8) otherwise Violation.PASSWORD
    }

val numberCcValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        hasLengthEqualTo(16) otherwise Violation.NUMBER_CC
        isAllDigits() otherwise Violation.NUMBER_CC
        constrain {
            // Validate the check digit (Luhn algorithm)
            val total =
                it.take(15).reversed().withIndex().sumOf { c ->
                    var digit = (c.value - '0') * (it.length - 1)
                    if (c.index % 2 == 0) digit *= 2
                    digit / 10 + digit % 2
                }
            val checkDigit = '0' + (10 - total % 10) % 10
            it.endsWith(checkDigit)
        } otherwise Violation.NUMBER_CC_CHECK
    }

val expirationDateCcRegex = Regex("""^(0[1-9]|1[0-2])[0-9]{2}""")
val expirationDateCcValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        isMatching(expirationDateCcRegex) otherwise Violation.EXPIRATION_DATE_CC
    }

val cvcCcValidator =
    Validator<String> {
        isNotBlank() otherwise Violation.REQUIRED
        hasLengthEqualTo(3) otherwise Violation.CVC_CC
        isAllDigits() otherwise Violation.CVC_CC
    }
