package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.AuthRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterStartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthGraph

@Destination<AuthGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun LoginRoute(
    navigator: DestinationsNavigator
) {
    LoginScreen(
        onLogin = { },
        onRegister = { navigator.navigate(RegisterStartRouteDestination) {
            popUpTo(AuthRouteDestination)
        } }
    )
}

@Composable
internal fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(24.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Column {
                    Text(
                        stringResource(R.string.login_title),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Text(
                        stringResource(R.string.login_subtitle),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                TextField(
                    value = "",
                    onValueChange = {},
                    label = {
                        Text(stringResource(R.string.email))
                    },
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = stringResource(R.string.email))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.fillMaxWidth()
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
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Column {
                Button(
                    onClick = { onLogin() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.login_action))
                }

                TextButton(
                    onClick = { onRegister() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.login_register_action))
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLogin = { },
        onRegister = { }
    )
}
