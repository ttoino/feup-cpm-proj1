package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
fun CartScanRoute(
    navigator: DestinationsNavigator,
    viewModel: CartScanViewModel = hiltViewModel(),
) {
    val cartCode by viewModel.cartCode.collectAsStateWithLifecycle()

    cartCode?.let {
        CartScanScreen(
            cartCode = it,
            onBack = { navigator.navigateUp() },
        )
    }
}

@Composable
fun CartScanScreen(
    cartCode: ImageBitmap,
    onBack: () -> Unit,
) {
    AppBarLayout(
        title = "Validate order",
        onBack = onBack,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(48.dp).fillMaxSize(),
    ) {
        Spacer(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .drawBehind {
                    drawImage(cartCode, dstSize = IntSize(size.width.toInt(), size.height.toInt()))
                },
        )
    }
}
