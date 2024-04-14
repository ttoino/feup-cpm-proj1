package pt.up.fe.cpm.tiktek.core.domain

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import pt.up.fe.cpm.tiktek.core.model.validation.Violation
import pt.up.fe.cpm.tiktek.core.ui.form.FormFieldState

class FormFieldUseCase<T>(
    initialValue: T,
    private val validator: Validator.Runner<T>,
    private val mapFn: (T) -> T = { it },
) {
    var state by mutableStateOf(FormFieldState(initialValue, getError(initialValue)))
        private set

    fun update(value: T) {
        val v = mapFn(value)
        val error = getError(v)

        state = state.copy(value = v, error = error)
    }

    fun showError() {
        state = state.copy(showError = true)
    }

    private fun getError(value: T) =
        when (val result = validator(value)) {
            is ValidationResult.Success -> null
            is ValidationResult.Failure -> result.violations.firstOrNull()?.message?.let { violationToResource(it) }
        }
}

@StringRes
internal fun violationToResource(violation: String): Int =
    try {
        when (Violation.valueOf(violation)) {
            Violation.REQUIRED -> R.string.violation_required
            Violation.NIF -> R.string.violation_nif
            Violation.NIF_CHECK -> R.string.violation_nif_check
            Violation.NUMBER_CC -> R.string.violation_number_cc
            Violation.NUMBER_CC_CHECK -> R.string.violation_number_cc_check
            Violation.EMAIL -> R.string.violation_email
            Violation.EXPIRATION_DATE_CC -> R.string.violation_expiration_date_cc
            Violation.CVC_CC -> R.string.violation_cvc_cc
            Violation.PASSWORD -> R.string.violation_password
            Violation.BIRTHDATE -> R.string.violation_birthdate
        }
    } catch (e: IllegalArgumentException) {
        R.string.violation_unknown
    }