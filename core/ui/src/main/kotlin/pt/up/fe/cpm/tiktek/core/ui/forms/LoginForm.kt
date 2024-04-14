package pt.up.fe.cpm.tiktek.core.ui.forms

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import pt.up.fe.cpm.tiktek.core.model.FormFieldState

@Composable
fun LoginForm(
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    lastImeAction: ImeAction = ImeAction.Done,
) {
    EmailField(
        emailState,
        onUpdateEmail,
        imeAction = ImeAction.Next,
        modifier = Modifier.fillMaxWidth(),
    )

    PasswordField(
        passwordState,
        onUpdatePassword,
        imeAction = lastImeAction,
        modifier = Modifier.fillMaxWidth(),
    )
}
