package pt.up.fe.cpm.tiktek

import androidx.compose.foundation.layout.WindowInsets
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
import com.ramcosta.composedestinations.generated.navgraphs.MainNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.TikTekNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import pt.up.fe.cpm.tiktek.core.ui.SnackbarProvider
import pt.up.fe.cpm.tiktek.core.ui.snackbar
import pt.up.fe.cpm.tiktek.navigation.BottomBar
import pt.up.fe.cpm.tiktek.navigation.TikTekNavHost
import pt.up.fe.cpm.tiktek.navigation.currentScreen
import pt.up.fe.cpm.tiktek.navigation.navigateToScreen
import timber.log.Timber

@Composable
fun MainActivity.MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val loggedIn by viewModel.loggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(loggedIn) {
        Timber.d("Logged in changed")
        navController.navigate(if (loggedIn ?: return@LaunchedEffect) MainNavGraph else AuthNavGraph) {
            popUpTo(TikTekNavGraph) {
                inclusive = true
            }
        }
    }

    splashScreen.setKeepOnScreenCondition { loggedIn == null }

    SnackbarProvider {
        Scaffold(
            bottomBar = {
                if (loggedIn == true) {
                    BottomBar(
                        navController.currentScreen,
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
                modifier = Modifier.padding(it),
            )
        }
    }
}
