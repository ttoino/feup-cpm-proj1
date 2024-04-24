package pt.up.fe.cpm.tiktek.feature.events.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.generated.events.destinations.EventConfirmationDialogDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDialogDestination
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import pt.up.fe.cpm.tiktek.core.ui.destinationsViewModel
import pt.up.fe.cpm.tiktek.feature.events.EventViewModel

@NavGraph<ExternalModuleGraph>
internal annotation class EventsGraph

@Composable
@SuppressLint("ComposableNaming")
fun DependenciesContainerBuilder<*>.eventsDependencies() {
    destinationsViewModel<EventViewModel>(
        EventDestination.route,
        EventDestination,
        EventDialogDestination,
        EventConfirmationDialogDestination,
    )
}
