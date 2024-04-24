package pt.up.fe.cpm.tiktek.feature.auth.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterFinishRouteDestination
import com.ramcosta.composedestinations.generated.auth.destinations.RegisterStartRouteDestination
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import pt.up.fe.cpm.tiktek.core.ui.destinationsViewModel
import pt.up.fe.cpm.tiktek.feature.auth.RegisterViewModel

@NavHostGraph
internal annotation class AuthGraph

@Composable
@SuppressLint("ComposableNaming")
fun DependenciesContainerBuilder<*>.authDependencies() {
    destinationsViewModel<RegisterViewModel>(
        RegisterStartRouteDestination.route,
        RegisterStartRouteDestination,
        RegisterFinishRouteDestination,
    )
}
