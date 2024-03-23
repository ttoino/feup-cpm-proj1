package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

@Destination<CafeteriaGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CafeteriaRoute() {
    // TODO: Get data

    CafeteriaScreen()
}

@Composable
internal fun CafeteriaScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Cafeteria")
    }
}
