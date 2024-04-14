package pt.up.fe.cpm.tiktek.core.model.validation

import dev.nesk.akkurate.constraints.constrainIfNotNull
import dev.nesk.akkurate.validatables.Validatable

internal fun Validatable<String?>.isAllDigits() = constrainIfNotNull { it.all { it.isDigit() } }
