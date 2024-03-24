package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.auth.destinations.LoginRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterStartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthGraph

@Destination<AuthGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun AuthRoute(navigator: DestinationsNavigator) {
    AuthScreen(
        onLogin = { navigator.navigate(LoginRouteDestination) },
        onRegister = { navigator.navigate(RegisterStartRouteDestination) },
    )
}

@Composable
internal fun AuthScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
) {
    Scaffold {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .padding(64.dp)
                    .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painterResource(R.drawable.logo),
                    "TikTek",
                    Modifier.aspectRatio(1f),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )

                Text(
                    "TikTek",
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Button(
                    onClick = { onLogin() },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.login_action))
                }

                Button(
                    onClick = { onRegister() },
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.register_action))
                }
            }
        }
    }
}
