package pt.up.fe.cpm.tiktek.feature.auth.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.NavHostGraph
import com.ramcosta.composedestinations.generated.auth.AuthNavGraphs
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthNavGraph
import com.ramcosta.composedestinations.navigation.DependenciesContainerBuilder
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.navigation.getBackStackEntry
import com.ramcosta.composedestinations.navigation.navGraph
import pt.up.fe.cpm.tiktek.feature.auth.RegisterViewModel

@NavHostGraph
internal annotation class AuthGraph

@Composable
@SuppressLint("ComposableNaming")
fun DependenciesContainerBuilder<*>.authDependencies() {
    navGraph(AuthNavGraph) {
        val parentEntry =
            remember(navBackStackEntry) {
                navController.getBackStackEntry(AuthNavGraphs.auth)
            }
        dependency(hiltViewModel<RegisterViewModel>(parentEntry))
    }
}
