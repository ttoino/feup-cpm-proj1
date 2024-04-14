package pt.up.fe.cpm.tiktek.feature.auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.DefaultFadingTransitions
import com.ramcosta.composedestinations.generated.auth.AuthNavGraphs
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.getBackStackEntry
import com.ramcosta.composedestinations.navigation.navGraph
import pt.up.fe.cpm.tiktek.feature.auth.RegisterViewModel

@Composable
fun AuthNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        navGraph = AuthNavGraphs.auth,
        navController = navController,
        modifier = modifier,
        defaultTransitions = DefaultFadingTransitions,
        dependenciesContainerBuilder = {
            navGraph(AuthNavGraphs.auth) {
                val parentEntry =
                    remember(navBackStackEntry) {
                        navController.getBackStackEntry(AuthNavGraphs.auth)
                    }
                dependency(hiltViewModel<RegisterViewModel>(parentEntry))
            }
        },
    )
}
