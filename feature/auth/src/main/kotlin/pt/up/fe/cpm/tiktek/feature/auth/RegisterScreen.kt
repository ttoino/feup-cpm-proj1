package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.AuthRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.LoginRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterFinishRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.core.ui.form.SeparatorVisualTransformation
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
        uiState = viewModel.uiState,
        onUpdateName = viewModel::updateName,
        onUpdateNif = viewModel::updateNif,
        onUpdateBirthdate = viewModel::updateBirthdate,
        onUpdateEmail = viewModel::updateEmail,
        onUpdatePassword = viewModel::updatePassword,
        onContinue = { navigator.navigate(RegisterFinishRouteDestination) },
        onLogin = {
            navigator.navigate(LoginRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
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
        onUpdateNameCc = viewModel::updateNameCc,
        onUpdateNumberCc = viewModel::updateNumberCc,
        onUpdateExpirationDateCc = viewModel::updateExpirationDateCc,
        onUpdateCvcCc = viewModel::updateCvcCc,
        onUpdateTermsAccepted = viewModel::updateTermsAccepted,
        onRegister = viewModel::register,
        onLogin = {
            navigator.navigate(LoginRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
    )
}

@Composable
internal fun RegisterStartScreen(
    uiState: RegisterUiState,
    onUpdateName: (String) -> Unit,
    onUpdateNif: (String) -> Unit,
    onUpdateBirthdate: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onContinue: () -> Unit,
    onLogin: () -> Unit,
) {
    AuthLayout(
        title = R.string.register_title,
        subtitle = R.string.register_start_subtitle,
        mainAction = R.string.register_continue_action,
        onMainAction = onContinue,
        secondaryAction = R.string.register_login_action,
        onSecondaryAction = onLogin,
    ) {
        TextField(
            value = uiState.name,
            onValueChange = onUpdateName,
            label = {
                Text(stringResource(R.string.name))
            },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = stringResource(R.string.name))
            },
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextField(
                value = uiState.nif,
                onValueChange = onUpdateNif,
                label = {
                    Text(stringResource(R.string.nif))
                },
                leadingIcon = {
                    Icon(Icons.Default.Pin, contentDescription = stringResource(R.string.nif))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                modifier = Modifier.weight(1f),
            )

            TextField(
                value = uiState.birthdate,
                onValueChange = onUpdateBirthdate,
                label = {
                    Text(stringResource(R.string.birthdate))
                },
                leadingIcon = {
                    Icon(Icons.Default.Today, contentDescription = stringResource(R.string.birthdate))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                modifier = Modifier.weight(1f),
            )
        }

        TextField(
            value = uiState.email,
            onValueChange = onUpdateEmail,
            label = {
                Text(stringResource(R.string.email))
            },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = stringResource(R.string.email))
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            modifier = Modifier.fillMaxWidth(),
        )

        TextField(
            value = uiState.password,
            onValueChange = onUpdatePassword,
            label = {
                Text(stringResource(R.string.password))
            },
            leadingIcon = {
                Icon(Icons.Default.Key, contentDescription = stringResource(R.string.password))
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Composable
internal fun RegisterFinishScreen(
    uiState: RegisterUiState,
    onUpdateNameCc: (String) -> Unit,
    onUpdateNumberCc: (String) -> Unit,
    onUpdateExpirationDateCc: (String) -> Unit,
    onUpdateCvcCc: (String) -> Unit,
    onUpdateTermsAccepted: (Boolean) -> Unit,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
) {
    AuthLayout(
        title = R.string.register_title,
        subtitle = R.string.register_finish_subtitle,
        mainAction = R.string.register_action,
        onMainAction = onRegister,
        mainActionDisabled = uiState.isLoading,
        secondaryAction = R.string.register_login_action,
        onSecondaryAction = onLogin,
        errorMessage = uiState.errorMessage,
    ) {
        TextField(
            value = uiState.nameCc,
            onValueChange = onUpdateNameCc,
            label = {
                Text(stringResource(R.string.name_cc))
            },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = stringResource(R.string.name_cc))
            },
            keyboardOptions =
                KeyboardOptions(
                    imeAction = ImeAction.Next,
                ),
            modifier = Modifier.fillMaxWidth(),
        )

        TextField(
            value = uiState.numberCc,
            onValueChange = onUpdateNumberCc,
            label = {
                Text(stringResource(R.string.number_cc))
            },
            leadingIcon = {
                Icon(Icons.Default.CreditCard, contentDescription = stringResource(R.string.number_cc))
            },
            keyboardOptions =
                KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next,
                ),
            visualTransformation = SeparatorVisualTransformation(4, maxSize = 16),
            modifier = Modifier.fillMaxWidth(),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextField(
                value = uiState.expirationDateCc,
                onValueChange = onUpdateExpirationDateCc,
                label = {
                    Text(stringResource(R.string.expiry_cc))
                },
                leadingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.expiry_cc))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                    ),
                visualTransformation = SeparatorVisualTransformation(2, '/', 4),
                modifier = Modifier.weight(1f),
            )

            TextField(
                value = uiState.cvcCc,
                onValueChange = onUpdateCvcCc,
                label = {
                    Text(stringResource(R.string.cvc_cc))
                },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = stringResource(R.string.cvc_cc))
                },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                modifier = Modifier.weight(1f),
            )
        }

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
                checked = uiState.termsAccepted,
                onCheckedChange = onUpdateTermsAccepted,
            )

            Text(stringResource(R.string.terms_and_conditions))
        }
    }
}
