package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.Configuration
import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable
import dev.nesk.akkurate.Validator as AkkurateValidator

val akkurateConfig =
    Configuration {
        failOnFirstViolation = true
        defaultViolationMessage = Violation.UNKNOWN.name
    }

@Suppress("ktlint:standard:function-naming")
fun <T> Validator(block: Validatable<T>.() -> Unit) = AkkurateValidator(akkurateConfig, block)

@Suppress("ktlint:standard:function-naming")
fun <T> SuspendableValidator(block: suspend Validatable<T>.() -> Unit) = AkkurateValidator.suspendable(akkurateConfig, block)

fun Validatable<String?>.isAllDigits() = constrainIfNotNull { it.all { it.isDigit() } }

infix fun Constraint.otherwise(violation: Violation) = this otherwise violation::name
