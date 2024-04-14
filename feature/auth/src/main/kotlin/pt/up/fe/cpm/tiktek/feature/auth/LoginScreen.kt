package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.AuthRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterStartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.forms.LoginForm
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthGraph
import pt.up.fe.cpm.tiktek.feature.auth.ui.AuthLayout

@Destination<AuthGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun LoginRoute(
    navigator: DestinationsNavigator,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginScreen(
        uiState = viewModel.uiState,
        emailState = viewModel.email.state,
        onUpdateEmail = viewModel.email::update,
        passwordState = viewModel.password.state,
        onUpdatePassword = viewModel.password::update,
        onLogin = viewModel::login,
        onRegister = {
            navigator.navigate(RegisterStartRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
        canLogin = viewModel.canLogin,
    )
}

@Composable
internal fun LoginScreen(
    uiState: LoginUiState,
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    canLogin: Boolean,
) {
    AuthLayout(
        title = R.string.login_title,
        subtitle = R.string.login_subtitle,
        mainAction = R.string.login_action,
        onMainAction = onLogin,
        mainActionDisabled = uiState.isLoading || !canLogin,
        secondaryAction = R.string.login_register_action,
        onSecondaryAction = onRegister,
        errorMessage = uiState.errorMessage,
    ) {
        LoginForm(
            emailState,
            onUpdateEmail,
            passwordState,
            onUpdatePassword,
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        uiState = LoginUiState(),
        emailState = FormFieldState(""),
        onUpdateEmail = { },
        passwordState = FormFieldState(""),
        onUpdatePassword = { },
        onLogin = { },
        onRegister = { },
        canLogin = true,
    )
}
