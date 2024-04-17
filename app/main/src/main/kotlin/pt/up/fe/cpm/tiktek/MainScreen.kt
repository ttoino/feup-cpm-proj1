package pt.up.fe.cpm.tiktek

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.ramcosta.composedestinations.generated.auth.navgraphs.AuthNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.MainNavGraph
import com.ramcosta.composedestinations.generated.navgraphs.TikTekNavGraph
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.navigation.popUpTo
import kotlinx.coroutines.flow.onEach
import pt.up.fe.cpm.tiktek.navigation.BottomBar
import pt.up.fe.cpm.tiktek.navigation.TikTekNavHost
import pt.up.fe.cpm.tiktek.navigation.currentScreen
import pt.up.fe.cpm.tiktek.navigation.navigateToScreen

@Composable
fun MainActivity.MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val loggedIn by viewModel.loggedIn.onEach {
        navController.navigate(if (it) MainNavGraph else AuthNavGraph) {
            popUpTo(TikTekNavGraph) {
                inclusive = true
            }
        }
    }.collectAsState(null)

    splashScreen.setKeepOnScreenCondition { loggedIn == null }

    Scaffold(
        bottomBar = {
            if (loggedIn == true) {
                BottomBar(
                    navController.currentScreen,
                    navController::navigateToScreen,
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        TikTekNavHost(
            navController = navController,
            modifier = Modifier.padding(it),
        )
    }
}
