package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import pt.up.fe.cpm.tiktek.core.ui.form.FormFieldState

@Composable
fun LoginForm(
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    onShowEmailError: () -> Unit,
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    onShowPasswordError: () -> Unit,
    lastImeAction: ImeAction = ImeAction.Done,
) {
    EmailField(
        emailState,
        onUpdateEmail,
        onShowEmailError,
        imeAction = ImeAction.Next,
        modifier = Modifier.fillMaxWidth(),
    )

    PasswordField(
        passwordState,
        onUpdatePassword,
        onShowPasswordError,
        imeAction = lastImeAction,
        modifier = Modifier.fillMaxWidth(),
    )
}
