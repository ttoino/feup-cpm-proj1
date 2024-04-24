package pt.up.fe.cpm.tiktek.tickets

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.generated.NavGraphs

@Composable
fun MainScreen() {
    Scaffold(contentWindowInsets = WindowInsets(0, 0, 0, 0)) {
        DestinationsNavHost(
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(it),
        )
    }
}
