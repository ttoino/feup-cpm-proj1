package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.Validator
import dev.nesk.akkurate.constraints.builders.hasLengthEqualTo
import dev.nesk.akkurate.constraints.builders.hasLengthGreaterThanOrEqualTo
import dev.nesk.akkurate.constraints.builders.isInPast
import dev.nesk.akkurate.constraints.builders.isMatching
import dev.nesk.akkurate.constraints.builders.isNotBlank
import dev.nesk.akkurate.constraints.builders.isNotNull
import dev.nesk.akkurate.constraints.constrain
import dev.nesk.akkurate.validatables.validatableOf
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toJavaLocalDate

val nameValidator =
    Validator<String> {
        isNotBlank()
    }

val nifValidator =
    Validator<String> {
        hasLengthEqualTo(9)
        isAllDigits()
        constrain {
            // Validate the check digit (mod 11 algorithm)
            val total = it.take(8).reversed().withIndex().sumOf { c -> (c.value - '0') * (c.index + 2) }
            val checkDigit = '0' + 11 - total % 11
            it.endsWith(checkDigit)
        }
    }

val birthdateValidator =
    Validator<LocalDate?> {
        isNotNull()
        validatableOf(LocalDate::toJavaLocalDate).isInPast()
    }

val emailRegex =
    Regex("""^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$""")
val emailValidator =
    Validator<String> {
        isMatching(emailRegex)
    }

val passwordValidator =
    Validator<String> {
        hasLengthGreaterThanOrEqualTo(8)
    }

val numberCcValidator =
    Validator<String> {
        hasLengthEqualTo(16)
        isAllDigits()
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
        }
    }

val expirationDateCcRegex = Regex("""^(0[1-9]|1[0-2])[0-9]{2}""")
val expirationDateCcValidator =
    Validator<String> {
        isMatching(expirationDateCcRegex)
    }

val cvcCcValidator =
    Validator<String> {
        hasLengthEqualTo(3)
        isAllDigits()
    }
