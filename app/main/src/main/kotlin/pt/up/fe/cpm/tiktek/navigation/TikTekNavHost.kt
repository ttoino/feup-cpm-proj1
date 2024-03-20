package pt.up.fe.cpm.tiktek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.ExternalNavGraph
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.NavGraphs
import com.ramcosta.composedestinations.generated.cafeteria.navgraphs.CafeteriaNavGraph
import com.ramcosta.composedestinations.generated.events.navgraphs.EventsNavGraph
import com.ramcosta.composedestinations.generated.profile.navgraphs.ProfileNavGraph
import com.ramcosta.composedestinations.generated.tickets.navgraphs.TicketsNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.currentDestinationAsState
import com.ramcosta.composedestinations.utils.startDestination

@NavHostGraph
annotation class TikTekGraph {
    @ExternalNavGraph<EventsNavGraph>(start = true)
    @ExternalNavGraph<TicketsNavGraph>
    @ExternalNavGraph<CafeteriaNavGraph>
    @ExternalNavGraph<ProfileNavGraph>
    companion object Includes
}

@Composable
fun TikTekNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    DestinationsNavHost(
        navGraph = NavGraphs.tikTek,
        navController = navController,
        modifier = modifier,
    )
}

fun NavController.navigateToScreen(screen: Screen) {
    val route = when (screen) {
        Screen.EVENTS -> EventsNavGraph
        Screen.TICKETS -> TicketsNavGraph
        Screen.CAFETERIA -> CafeteriaNavGraph
        Screen.PROFILE -> ProfileNavGraph
    }

    navigate(route) {
        popUpTo(graph.startDestinationId) {
//            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

val NavController.currentScreen
    @Composable get() = when (currentDestinationAsState().value?.startDestination) {
        EventsNavGraph.startDestination -> Screen.EVENTS
        TicketsNavGraph.startDestination -> Screen.TICKETS
        CafeteriaNavGraph.startDestination -> Screen.CAFETERIA
        ProfileNavGraph.startDestination -> Screen.PROFILE
        else -> null
    }
