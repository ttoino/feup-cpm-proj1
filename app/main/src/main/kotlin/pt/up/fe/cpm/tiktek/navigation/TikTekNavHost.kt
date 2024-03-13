package pt.up.fe.cpm.tiktek.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.util.trace
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CAFETERIA_ROUTE
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.cafeteriaScreen
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.navigateToCafeteria
import pt.up.fe.cpm.tiktek.feature.events.navigation.EVENTS_ROUTE
import pt.up.fe.cpm.tiktek.feature.events.navigation.eventsScreen
import pt.up.fe.cpm.tiktek.feature.events.navigation.navigateToEvents
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.TICKETS_ROUTE
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.navigateToTickets
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.ticketsScreen

@Composable
fun TikTekNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = EVENTS_ROUTE,
        modifier = modifier
    ) {
        eventsScreen()
        ticketsScreen()
        cafeteriaScreen()
    }
}

fun NavHostController.navigateToScreen(screen: Screen) {
    trace("Navigation: ${screen.name}") {
        val topLevelNavOptions = navOptions {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }

        when (screen) {
            Screen.EVENTS -> navigateToEvents(topLevelNavOptions)
            Screen.TICKETS -> navigateToTickets(topLevelNavOptions)
            Screen.CAFETERIA -> navigateToCafeteria(topLevelNavOptions)
            Screen.PROFILE -> Unit
        }
    }
}

val NavHostController.currentDestinationComposable
    @Composable get() = currentBackStackEntryAsState().value?.destination

val NavHostController.currentScreen
    @Composable get() = when (currentDestinationComposable?.route) {
        EVENTS_ROUTE -> Screen.EVENTS
        TICKETS_ROUTE -> Screen.TICKETS
        CAFETERIA_ROUTE -> Screen.CAFETERIA
        else -> null
    }
