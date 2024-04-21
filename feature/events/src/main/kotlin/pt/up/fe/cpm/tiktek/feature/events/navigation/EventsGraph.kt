package pt.up.fe.cpm.tiktek.feature.events.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.generated.events.destinations.EventConfirmationDialogDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDialogDestination
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.destination
import pt.up.fe.cpm.tiktek.feature.events.EventViewModel

@NavGraph<ExternalModuleGraph>
internal annotation class EventsGraph

@Composable
@SuppressLint("ComposableNaming")
fun DependenciesContainerBuilder<*>.eventsDependencies() {
    destination(EventDestination) {
        val parentEntry =
            remember(navBackStackEntry) {
                navController.getBackStackEntry(EventDestination.route)
            }
        dependency(hiltViewModel<EventViewModel>(parentEntry))
    }
    destination(EventDialogDestination) {
        val parentEntry =
            remember(navBackStackEntry) {
                navController.getBackStackEntry(EventDestination.route)
            }
        dependency(hiltViewModel<EventViewModel>(parentEntry))
    }
    destination(EventConfirmationDialogDestination) {
        val parentEntry =
            remember(navBackStackEntry) {
                navController.getBackStackEntry(EventDestination.route)
            }
        dependency(hiltViewModel<EventViewModel>(parentEntry))
    }
}
