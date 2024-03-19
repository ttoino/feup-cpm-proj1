package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

@Destination<EventsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun EventsRoute() {
    // TODO: Get data

    EventsScreen()
}

@Composable
internal fun EventsScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Events")
    }
}
