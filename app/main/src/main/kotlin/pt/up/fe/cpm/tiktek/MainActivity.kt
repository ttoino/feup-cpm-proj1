package pt.up.fe.cpm.tiktek

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pt.up.fe.cpm.tiktek.core.ui.ScreenActivity

@AndroidEntryPoint
class MainActivity : ScreenActivity() {
    @Composable
    override fun Screen() = MainScreen()
}
