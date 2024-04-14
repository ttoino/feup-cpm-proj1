package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.R
import pt.up.fe.cpm.tiktek.core.ui.form.FormField
import pt.up.fe.cpm.tiktek.core.ui.form.PasswordFormField

@Composable
internal fun EmailField(
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Default,
) {
    FormField(
        state = emailState,
        onValueChange = onUpdateEmail,
        label = stringResource(R.string.email),
        leadingIcon = {
            Icon(Icons.Default.Email, contentDescription = stringResource(R.string.email))
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = imeAction,
            ),
        modifier = modifier,
    )
}

@Composable
internal fun PasswordField(
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    modifier: Modifier = Modifier,
    @StringRes label: Int = R.string.password,
    imeAction: ImeAction = ImeAction.Default,
) {
    PasswordFormField(
        state = passwordState,
        onValueChange = onUpdatePassword,
        label = stringResource(label),
        leadingIcon = {
            Icon(Icons.Default.Key, contentDescription = stringResource(label))
        },
        keyboardOptions =
            KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction,
            ),
        modifier = modifier,
    )
}
