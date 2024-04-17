package pt.up.fe.cpm.tiktek.core.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import pt.up.fe.cpm.tiktek.core.ui.theme.TikTekTheme

abstract class ScreenActivity : ComponentActivity() {
    lateinit var splashScreen: SplashScreen

    @Composable
    abstract fun Screen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreen = installSplashScreen()

        enableEdgeToEdge()

        setContent {
            TikTekTheme {
                Screen()
            }
        }
    }
}
