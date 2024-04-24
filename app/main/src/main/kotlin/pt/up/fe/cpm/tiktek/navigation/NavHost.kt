package pt.up.fe.cpm.tiktek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.navgraphs.AuthenticatedNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.TikTekNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.core.ui.transition.ForwardBackwardTransition
import pt.up.fe.cpm.tiktek.feature.auth.navigation.authDependencies
import pt.up.fe.cpm.tiktek.feature.events.navigation.eventsDependencies

@Composable
fun TikTekNavHost(
    navController: NavHostController,
    modifier: Modifier,
) {
    DestinationsNavHost(
        navGraph = TikTekNavGraph,
        navController = navController,
        modifier = modifier,
        defaultTransitions = ForwardBackwardTransition,
        dependenciesContainerBuilder = {
            authDependencies()
            eventsDependencies()
        },
    )
}

fun NavController.navigateToScreen(screen: Screen) {
    navigate(screen.graph) {
        popUpTo(AuthenticatedNavGraph) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
