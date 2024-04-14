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
    var state by mutableStateOf(FormFieldState(initialValue))
        private set

    fun update(value: T) {
        val v = mapFn(value)
        val error =
            when (val result = validator(v)) {
                is ValidationResult.Success -> ""
                is ValidationResult.Failure -> result.violations.joinToString()
            }

        state = state.copy(value = v, error = error, showError = error != "")
    }
}
