package pt.up.fe.cpm.tiktek.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.LoadState
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.CardRow

data class Args(val qrCodeResult: String)

@Destination<RootGraph>(
    navArgs = Args::class,
)
@Composable
internal fun TicketValidatedRoute(
    navigator: DestinationsNavigator,
    viewModel: TicketValidatedViewModel = hiltViewModel(),
) {
    val ticket by viewModel.returnedTicket.collectAsStateWithLifecycle()

    TicketValidatedScreen(
        ticket = ticket,
        onBack = { navigator.navigateUp() },
    )
}

@Composable
fun TicketValidatedScreen(
    ticket: LoadState<TicketWithEvent>,
    onBack: () -> Unit,
) {
    AppBarLayout(
        title = "Compra Bilhete",
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        when (ticket) {
            is LoadState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Text("Error: ${ticket.message}")
            }
            is LoadState.Success -> {
                Text(
                    text = "Bilhete validado com sucesso",
                    style = MaterialTheme.typography.headlineSmall,
                )
                CardRow(
                    title = ticket.value.event.name,
                    subtitle = "Seat ${ticket.value.seat}",
                    imageUrl = ticket.value.event.imageUrl,
                )
            }
        }
    }
}
