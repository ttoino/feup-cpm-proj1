package pt.up.fe.cpm.tiktek

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import pt.up.fe.cpm.tiktek.navigation.BottomBar
import pt.up.fe.cpm.tiktek.navigation.TikTekNavHost
import pt.up.fe.cpm.tiktek.navigation.currentScreen
import pt.up.fe.cpm.tiktek.navigation.navigateToScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

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
}
