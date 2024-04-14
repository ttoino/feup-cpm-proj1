package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.R

@Composable
fun PasswordForm(
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    lastImeAction: ImeAction = ImeAction.Done,
) {
    PasswordField(
        passwordState,
        onUpdatePassword,
        modifier = Modifier.fillMaxWidth(),
        imeAction = lastImeAction,
    )
}

@Composable
fun UpdatePasswordForm(
    oldPasswordState: FormFieldState<String>,
    onUpdateOldPassword: (String) -> Unit,
    newPasswordState: FormFieldState<String>,
    onUpdateNewPassword: (String) -> Unit,
    lastImeAction: ImeAction = ImeAction.Done,
) {
    PasswordField(
        oldPasswordState,
        onUpdateOldPassword,
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        label = R.string.password_old,
    )

    PasswordField(
        newPasswordState,
        onUpdateNewPassword,
        modifier = Modifier.fillMaxWidth(),
        imeAction = lastImeAction,
        label = R.string.password_new,
    )
}
