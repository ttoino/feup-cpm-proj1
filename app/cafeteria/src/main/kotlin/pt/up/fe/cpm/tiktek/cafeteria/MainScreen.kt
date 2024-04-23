package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pt.up.fe.cpm.tiktek.cafeteria.navigation.CafetariaTerminalNavHost

@Composable
fun MainScreen() {
    Scaffold {
        Column(
            modifier = Modifier.padding(it),
        ) {
            CafetariaTerminalNavHost()
        }
    }
}
