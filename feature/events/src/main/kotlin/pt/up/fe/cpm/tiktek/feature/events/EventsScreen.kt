package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

@Destination<EventsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun EventsRoute(navigator: DestinationsNavigator) {
    // TODO: Get data

    EventsScreen()

    Button(
        onClick = { navigator.navigate(EventDestination("")) },
        modifier =
            Modifier
                .offset(y = 100.dp),
    ) {
        Text("Event")
    }
}

@Composable
internal fun EventsScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text("Events")
    }
}
