package pt.up.fe.cpm.tiktek.tickets.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

@Composable
fun TicketsTerminalNavHost() {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
    )
}
