package pt.up.fe.cpm.tiktek.feature.tickets

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
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.TicketsGraph

internal data class TicketArgs(val ticketId: String)

@Destination<TicketsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    navArgs = TicketArgs::class,
)
@Composable
fun TicketScanRoute(
    navigator: DestinationsNavigator,
    viewModel: TicketScanViewModel = hiltViewModel(),
) {
    val ticketCode by viewModel.ticketCode.collectAsStateWithLifecycle()

    ticketCode?.let {
        TicketScanScreen(
            ticketCode = it,
            onBack = { navigator.navigateUp() },
        )
    }
}

@Composable
fun TicketScanScreen(
    ticketCode: ImageBitmap,
    onBack: () -> Unit,
) {
    AppBarLayout(
        title = "Validate ticket",
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
                    drawImage(ticketCode, dstSize = IntSize(size.width.toInt(), size.height.toInt()))
                },
        )
    }
}
