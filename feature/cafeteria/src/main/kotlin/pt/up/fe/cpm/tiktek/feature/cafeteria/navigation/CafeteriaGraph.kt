package pt.up.fe.cpm.tiktek.feature.cafeteria.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.ExternalModuleGraph
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartConfirmDialogDestination
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartRouteDestination
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import pt.up.fe.cpm.tiktek.core.ui.destinationsViewModel
import pt.up.fe.cpm.tiktek.feature.cafeteria.CartViewModel

@NavGraph<ExternalModuleGraph>
internal annotation class CafeteriaGraph

@Composable
@SuppressLint("ComposableNaming")
fun DependenciesContainerBuilder<*>.cafeteriaDependencies() {
    destinationsViewModel<CartViewModel>(
        CartRouteDestination.route,
        CartRouteDestination,
        CartConfirmDialogDestination,
    )
}
