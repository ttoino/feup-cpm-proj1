package pt.up.fe.cpm.tiktek.core.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.destination
import com.ramcosta.composedestinations.spec.DestinationSpec

@Composable
@SuppressLint("ComposableNaming")
inline fun <reified VM : ViewModel> DependenciesContainerBuilder<*>.destinationsViewModel(
    route: String,
    vararg destinations: DestinationSpec,
) {
    destinations.forEach { destinationSpec ->
        destination(destinationSpec) {
            val parentEntry =
                remember(navBackStackEntry) {
                    navController.getBackStackEntry(route)
                }
            dependency(hiltViewModel<VM>(parentEntry))
        }
    }
}
