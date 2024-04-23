package pt.up.fe.cpm.tiktek.tickets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import pt.up.fe.cpm.tiktek.tickets.navigation.TicketsTerminalNavHost

@Composable
fun MainScreen() {
    Scaffold {
        Column(
            modifier =
                Modifier.padding(it),
        ) {
            TicketsTerminalNavHost()
        }
    }
}
