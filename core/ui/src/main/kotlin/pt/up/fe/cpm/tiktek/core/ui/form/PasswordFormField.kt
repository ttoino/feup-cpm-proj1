package pt.up.fe.cpm.tiktek.core.ui.form

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import pt.up.fe.cpm.tiktek.core.model.FormFieldState

@Composable
fun PasswordFormField(
    state: FormFieldState<String>,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Password,
        ),
    helperText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onLoseFocus: () -> Unit = {},
) {
    var showPassword by remember { mutableStateOf(false) }

    FormField(
        state = state,
        onValueChange = onValueChange,
        label = label,
        modifier = modifier,
        readOnly = readOnly,
        enabled = enabled,
        visualTransformation = if (showPassword) visualTransformation else PasswordVisualTransformation(),
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        helperText = helperText,
        leadingIcon = leadingIcon,
        trailingIcon =
            trailingIcon ?: {
                IconButton(
                    onClick = {
                        showPassword = !showPassword
                    },
                ) {
                    if (showPassword) {
                        Icon(Icons.Default.Visibility, "Password shown")
                    } else {
                        Icon(
                            Icons.Default.VisibilityOff,
                            "Password not shown",
                        )
                    }
                }
            },
        onLoseFocus = onLoseFocus,
    )
}
