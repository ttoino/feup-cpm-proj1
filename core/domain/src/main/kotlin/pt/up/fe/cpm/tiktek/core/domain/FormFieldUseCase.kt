package pt.up.fe.cpm.tiktek.core.domain

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dev.nesk.akkurate.ValidationResult
import dev.nesk.akkurate.Validator
import pt.up.fe.cpm.tiktek.core.model.FormFieldState

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
            is ValidationResult.Success -> ""
            is ValidationResult.Failure -> result.violations.firstOrNull()?.message ?: ""
        }
}
