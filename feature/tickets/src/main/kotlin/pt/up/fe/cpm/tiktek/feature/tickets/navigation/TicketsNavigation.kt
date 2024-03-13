package pt.up.fe.cpm.tiktek.feature.tickets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pt.up.fe.cpm.tiktek.feature.tickets.TicketsRoute

const val LINKED_TICKET_ID = "ticketId"
const val TICKETS_ROUTE = "tickets/{$LINKED_TICKET_ID}"
private const val TICKET_DEEP_LINK =
    "https://tiktek.cpm.fe.up.pt/$TICKETS_ROUTE"

fun NavController.navigateToTickets(navOptions: NavOptions) = navigate(TICKETS_ROUTE, navOptions)

fun NavGraphBuilder.ticketsScreen() {
    composable(
        route = TICKETS_ROUTE,
        deepLinks = listOf(
            navDeepLink { uriPattern = TICKET_DEEP_LINK }
        ),
        arguments = listOf(
            navArgument(LINKED_TICKET_ID) { type = NavType.StringType }
        )
    ) {
        TicketsRoute()
    }
}
