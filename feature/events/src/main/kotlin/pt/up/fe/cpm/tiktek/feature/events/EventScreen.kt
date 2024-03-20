package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.annotation.parameters.FULL_ROUTE_PLACEHOLDER
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

@Destination<EventsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    deepLinks = [
        DeepLink(uriPattern = "tiktek://$FULL_ROUTE_PLACEHOLDER"),
    ],
    route = "event"
)
@Composable
internal fun EventRoute(
    eventId: String
) {
    EventScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("O Pato Lindo") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
        ) {
            Box {
                AsyncImage(
                    model = "https://i.pinimg.com/originals/ee/78/c6/ee78c67c41f6439bb9ce406907c91f3d.jpg",
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .clip(MaterialTheme.shapes.medium)
                )
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 8.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(constraints)
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(0, placeable.height / 2)
                            }
                        },
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to cart")
                }
            }

            InfoRow(icon = Icons.Default.Event, primaryText = "Terça, 3 de maio, 2024", secondaryText = "10:00 - 12:00")
            InfoRow(icon = Icons.Default.LocationOn, primaryText = "Tasca Teatral - Sala B", secondaryText = "Rua do Infante, nº23, Porto, 4444-000")
            InfoRow(icon = Icons.Default.ConfirmationNumber, primaryText = "10€")

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(text = "Descrição", style = MaterialTheme.typography.headlineSmall)
                Text("É um pato formoso que dança ao ritmo das ondas do rio, suas penas reluzem sob o sol matinal, enquanto ele desliza gracioso pela água. Em \"O Pato Lindo\", mergulhe em um conto encantador bla bla bla")
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    primaryText: String,
    secondaryText: String? = null,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Column {
            Text(
                primaryText,
                style = MaterialTheme.typography.labelMedium
            )
            if (secondaryText != null) Text(
                secondaryText,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(
    device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480"
)
@Composable
private fun EventScreenPreview() {
    EventScreen()
}
