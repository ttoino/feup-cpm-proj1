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
import pt.up.fe.cpm.tiktek.feature.auth.navigation.AuthNavHost
import pt.up.fe.cpm.tiktek.navigation.BottomBar
import pt.up.fe.cpm.tiktek.navigation.TikTekNavHost
import pt.up.fe.cpm.tiktek.navigation.currentScreen
import pt.up.fe.cpm.tiktek.navigation.navigateToScreen

@Composable
fun MainActivity.MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val loggedIn by viewModel.loggedIn.collectAsState(null)

    splashScreen.setKeepOnScreenCondition { loggedIn == null }

    loggedIn?.let {
        if (it) {
            Scaffold(
                bottomBar = {
                    BottomBar(
                        navController.currentScreen,
                        navController::navigateToScreen,
                    )
                },
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
            ) {
                TikTekNavHost(
                    navController = navController,
                    modifier = Modifier.padding(it),
                )
            }
        } else {
            AuthNavHost(
                navController = navController,
            )
        }
    }
}
