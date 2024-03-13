package pt.up.fe.cpm.tiktek.core.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable

abstract class ScreenActivity : ComponentActivity() {
    @Composable
    abstract fun Screen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            TikTekTheme {
                Screen()
            }
        }
    }
}