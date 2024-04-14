package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.constraints.Constraint
import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.constraints.otherwise
import dev.nesk.akkurate.validatables.Validatable

internal fun Validatable<String?>.isAllDigits() = constrainIfNotNull { it.all { it.isDigit() } }

internal infix fun Constraint.otherwise(violation: Violation) = this otherwise violation::name
