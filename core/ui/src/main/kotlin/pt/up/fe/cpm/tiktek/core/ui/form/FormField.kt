package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation

data class FormFieldState<out T>(
    val value: T,
    @StringRes val error: Int? = null,
    val showError: Boolean = false,
) {
    val valid get() = error == null

    fun <R> map(fn: (T) -> R): FormFieldState<R> =
        FormFieldState(
            fn(value),
            error,
            showError,
        )
}

@Composable
fun FormField(
    state: FormFieldState<String>,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    helperText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onLoseFocus: () -> Unit = {},
) {
    var isFocused by remember { mutableStateOf(false) }
    val isError = state.showError && !state.valid

    TextField(
        value = state.value,
        isError = isError,
        supportingText =
            if (isError) {
                (
                    {
                        Text(stringResource(state.error!!))
                    }
                )
            } else {
                helperText
            },
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier =
            modifier.onFocusChanged {
                if (isFocused && !it.hasFocus) onLoseFocus()
                isFocused = it.hasFocus
            },
        readOnly = readOnly,
        enabled = enabled,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
    )
}
