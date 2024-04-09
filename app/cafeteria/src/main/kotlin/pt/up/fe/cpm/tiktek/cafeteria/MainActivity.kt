package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.runtime.Composable
import dagger.hilt.android.AndroidEntryPoint
import pt.up.fe.cpm.tiktek.core.app.ScreenActivity

@AndroidEntryPoint
class MainActivity : ScreenActivity() {
    @Composable
    override fun Screen() = PurchasedProductsScreen()
}
