package pt.up.fe.cpm.tiktek.feature.cafeteria.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import pt.up.fe.cpm.tiktek.feature.cafeteria.CafeteriaRoute

const val CAFETERIA_ROUTE = "cafeteria"

fun NavController.navigateToCafeteria(navOptions: NavOptions) = navigate(CAFETERIA_ROUTE, navOptions)

fun NavGraphBuilder.cafeteriaScreen() {
    composable(
        route = CAFETERIA_ROUTE
    ) {
        CafeteriaRoute()
    }
}
