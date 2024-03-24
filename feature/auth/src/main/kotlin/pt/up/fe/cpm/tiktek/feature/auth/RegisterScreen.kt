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
internal fun RegisterStartRoute(navigator: DestinationsNavigator) {
    RegisterStartScreen(
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
internal fun RegisterFinishRoute(navigator: DestinationsNavigator) {
    RegisterFinishScreen(
        onRegister = { },
        onLogin = {
            navigator.navigate(LoginRouteDestination) {
                popUpTo(AuthRouteDestination)
            }
        },
    )
}

@Composable
internal fun RegisterStartScreen(
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
            value = "",
            onValueChange = {},
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
                value = "",
                onValueChange = {},
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
                value = "",
                onValueChange = {},
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
            value = "",
            onValueChange = {},
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
            value = "",
            onValueChange = {},
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
    onRegister: () -> Unit,
    onLogin: () -> Unit,
) {
    AuthLayout(
        title = R.string.register_title,
        subtitle = R.string.register_finish_subtitle,
        mainAction = R.string.register_action,
        onMainAction = onRegister,
        secondaryAction = R.string.register_login_action,
        onSecondaryAction = onLogin,
    ) {
        TextField(
            value = "",
            onValueChange = {},
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
            value = "",
            onValueChange = { },
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
                value = "",
                onValueChange = { },
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
                value = "",
                onValueChange = {},
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
                checked = false,
                onCheckedChange = { },
            )

            Text(stringResource(R.string.terms_and_conditions))
        }
    }
}
