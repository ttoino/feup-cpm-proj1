package pt.up.fe.cpm.tiktek.core.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import pt.up.fe.cpm.tiktek.core.ui.theme.TikTekTheme

abstract class ScreenActivity : FragmentActivity() {
    lateinit var splashScreen: SplashScreen

    @Composable
    abstract fun Screen()

    override fun onCreate(savedInstanceState: Bundle?) {
        splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TikTekTheme {
                Screen()
            }
        }
    }
}
