package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.AuthRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterStartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthGraph
import pt.up.fe.cpm.tiktek.feature.auth.ui.AuthLayout

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
    AuthLayout(
        title = R.string.login_title,
        subtitle = R.string.login_subtitle,
        mainAction = R.string.login_action,
        onMainAction = onLogin,
        secondaryAction = R.string.login_register_action,
        onSecondaryAction = onRegister
    ) {
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
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLogin = { },
        onRegister = { }
    )
}
