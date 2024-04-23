package pt.up.fe.cpm.tiktek.cafeteria.navigation

import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

@Composable
fun CafetariaTerminalNavHost() {
    DestinationsNavHost(
        navGraph = NavGraphs.root,
    )
}
