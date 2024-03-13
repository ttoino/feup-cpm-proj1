package pt.up.fe.cpm.tiktek.feature.events.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pt.up.fe.cpm.tiktek.feature.events.EventsRoute

const val LINKED_EVENT_ID = "eventId"
const val EVENTS_ROUTE = "events/{$LINKED_EVENT_ID}"
private const val EVENT_DEEP_LINK =
    "https://tiktek.cpm.fe.up.pt/$EVENTS_ROUTE"

fun NavController.navigateToEvents(navOptions: NavOptions) = navigate(EVENTS_ROUTE, navOptions)

fun NavGraphBuilder.eventsScreen() {
    composable(
        route = EVENTS_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = EVENT_DEEP_LINK }
        ),
        arguments = listOf(
            navArgument(LINKED_EVENT_ID) { type = NavType.StringType }
        )
    ) {
        EventsRoute()
    }
}
