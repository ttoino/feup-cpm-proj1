package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.AuthRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.LoginRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterFinishRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.datetime.LocalDate
import pt.up.fe.cpm.tiktek.core.model.FormFieldState
import pt.up.fe.cpm.tiktek.core.ui.forms.PasswordForm
import pt.up.fe.cpm.tiktek.core.ui.forms.PaymentInformationForm
import pt.up.fe.cpm.tiktek.core.ui.forms.PersonalInformationForm
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthGraph
import pt.up.fe.cpm.tiktek.feature.auth.ui.AuthLayout

@Destination<AuthGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun RegisterStartRoute(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel,
) {
    RegisterStartScreen(
        nameState = viewModel.name.state,
        onUpdateName = viewModel.name::update,
        nifState = viewModel.nif.state,
        onUpdateNif = viewModel.nif::update,
        birthdateState = viewModel.birthdate.state,
        onUpdateBirthdate = viewModel.birthdate::update,
        emailState = viewModel.email.state,
        onUpdateEmail = viewModel.email::update,
        passwordState = viewModel.password.state,
        onUpdatePassword = viewModel.password::update,
        onContinue = { navigator.navigate(RegisterFinishRouteDestination) },
        onLogin = {
            navigator.navigate(LoginRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
        canContinue = viewModel.canContinue,
    )
}

@Destination<AuthGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun RegisterFinishRoute(
    navigator: DestinationsNavigator,
    viewModel: RegisterViewModel,
) {
    RegisterFinishScreen(
        uiState = viewModel.uiState,
        nameCcState = viewModel.nameCc.state,
        onUpdateNameCc = viewModel.nameCc::update,
        numberCcState = viewModel.numberCc.state,
        onUpdateNumberCc = viewModel.numberCc::update,
        expirationDateCcState = viewModel.expirationDateCc.state,
        onUpdateExpirationDateCc = viewModel.expirationDateCc::update,
        cvcCcState = viewModel.cvcCc.state,
        onUpdateCvcCc = viewModel.cvcCc::update,
        termsAcceptedState = viewModel.termsAccepted.state,
        onUpdateTermsAccepted = viewModel.termsAccepted::update,
        onRegister = viewModel::register,
        onLogin = {
            navigator.navigate(LoginRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
        canRegister = viewModel.canRegister,
    )
}

@Composable
internal fun RegisterStartScreen(
    nameState: FormFieldState<String>,
    onUpdateName: (String) -> Unit,
    nifState: FormFieldState<String>,
    onUpdateNif: (String) -> Unit,
    birthdateState: FormFieldState<LocalDate?>,
    onUpdateBirthdate: (LocalDate?) -> Unit,
    emailState: FormFieldState<String>,
    onUpdateEmail: (String) -> Unit,
    passwordState: FormFieldState<String>,
    onUpdatePassword: (String) -> Unit,
    onContinue: () -> Unit,
    onLogin: () -> Unit,
    canContinue: Boolean,
) {
    AuthLayout(
        title = R.string.register_title,
        subtitle = R.string.register_start_subtitle,
        mainAction = R.string.register_continue_action,
        onMainAction = onContinue,
        mainActionDisabled = !canContinue,
        secondaryAction = R.string.register_login_action,
        onSecondaryAction = onLogin,
    ) {
        PersonalInformationForm(
            nameState,
            onUpdateName,
            nifState,
            onUpdateNif,
            birthdateState,
            onUpdateBirthdate,
            emailState,
            onUpdateEmail,
            lastImeAction = ImeAction.Next,
        )

        PasswordForm(
            passwordState,
            onUpdatePassword,
        )
    }
}

@Composable
internal fun RegisterFinishScreen(
    uiState: RegisterUiState,
    nameCcState: FormFieldState<String>,
    onUpdateNameCc: (String) -> Unit,
    numberCcState: FormFieldState<String>,
    onUpdateNumberCc: (String) -> Unit,
    expirationDateCcState: FormFieldState<String>,
    onUpdateExpirationDateCc: (String) -> Unit,
    cvcCcState: FormFieldState<String>,
    onUpdateCvcCc: (String) -> Unit,
    termsAcceptedState: FormFieldState<Boolean>,
    onUpdateTermsAccepted: (Boolean) -> Unit,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    canRegister: Boolean,
) {
    AuthLayout(
        title = R.string.register_title,
        subtitle = R.string.register_finish_subtitle,
        mainAction = R.string.register_action,
        onMainAction = onRegister,
        mainActionDisabled = uiState.isLoading || !canRegister,
        secondaryAction = R.string.register_login_action,
        onSecondaryAction = onLogin,
        errorMessage = uiState.errorMessage,
    ) {
        PaymentInformationForm(
            nameCcState,
            onUpdateNameCc,
            numberCcState,
            onUpdateNumberCc,
            expirationDateCcState,
            onUpdateExpirationDateCc,
            cvcCcState,
            onUpdateCvcCc,
        )

        Text(
            stringResource(R.string.data_collection_advisory),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Checkbox(
                checked = termsAcceptedState.value,
                onCheckedChange = onUpdateTermsAccepted,
            )

            Text(stringResource(R.string.terms_and_conditions))
        }
    }
}
