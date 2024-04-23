package pt.up.fe.cpm.tiktek

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.AuthenticatedNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.TikTekNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.utils.isDirectionOnBackStack
import com.ramcosta.composedestinations.utils.isRouteOnBackStackAsState
import pt.up.fe.cpm.tiktek.core.ui.SnackbarProvider
import pt.up.fe.cpm.tiktek.core.ui.snackbar
import pt.up.fe.cpm.tiktek.navigation.BottomBar
import pt.up.fe.cpm.tiktek.navigation.TikTekNavHost
import pt.up.fe.cpm.tiktek.navigation.navigateToScreen
import timber.log.Timber

@Composable
fun MainActivity.MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val loggedIn by viewModel.loggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(loggedIn) {
        try {
            val route = if (loggedIn ?: return@LaunchedEffect) AuthenticatedNavGraph else AuthNavGraph

            if (!navController.isDirectionOnBackStack(route)) {
                navController.navigate(route) {
                    popUpTo(TikTekNavGraph.route) {
                        inclusive = true
                    }
                }
            }
        } catch (e: IllegalStateException) {
            Timber.e(e)
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }

    splashScreen.setKeepOnScreenCondition { loggedIn == null }

    SnackbarProvider {
        Scaffold(
            bottomBar = {
                AnimatedVisibility(
                    visible = loggedIn == true,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it },
                ) {
                    BottomBar(
                        { navController.isRouteOnBackStackAsState(it.graph) },
                        navController::navigateToScreen,
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(snackbar)
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            TikTekNavHost(
                navController = navController,
                modifier = Modifier.padding(it).fillMaxSize(),
            )
        }
    }
}
