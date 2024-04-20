package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.annotation.parameters.FULL_ROUTE_PLACEHOLDER
import com.ramcosta.composedestinations.generated.events.destinations.EventConfirmationDialogDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.generated.events.destinations.EventDialogDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.ui.relativeOffset
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

@Destination<EventsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    deepLinks = [
        DeepLink(uriPattern = "tiktek://$FULL_ROUTE_PLACEHOLDER"),
    ],
    route = "event",
)
@Composable
internal fun EventRoute(
    eventId: String,
    navigator: DestinationsNavigator,
    viewModel: EventViewModel = hiltViewModel(),
) {
    val event by viewModel.event.collectAsStateWithLifecycle()

    event?.let { EventScreen(navigator, it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventScreen(
    navigator: DestinationsNavigator,
    event: Event,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(event.name) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
        ) {
            Box {
                AsyncImage(
                    model = event.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .clip(MaterialTheme.shapes.medium),
                )
                FloatingActionButton(
                    onClick = { navigator.navigate(EventDialogDestination(event.id)) },
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(horizontal = 8.dp)
                            .relativeOffset(y = 0.5f),
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to cart")
                }
            }

            InfoRow(
                icon = Icons.Default.Event,
                primaryText =
                    stringResource(
                        R.string.date_value,
                        event.date.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
                    ),
                secondaryText =
                    stringResource(
                        R.string.time_range_value,
                        event.startTime.toMillisecondOfDay().toLong(),
                        event.endTime.toMillisecondOfDay().toLong(),
                    ),
            )
            InfoRow(
                icon = Icons.Default.LocationOn,
                primaryText = event.location,
                secondaryText = event.locationDetails,
            )
            InfoRow(icon = Icons.Default.ConfirmationNumber, primaryText = stringResource(R.string.price_value, event.price / 100f))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(text = stringResource(R.string.description), style = MaterialTheme.typography.headlineSmall)
                Text(event.description)
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
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
        )
        Column {
            Text(
                primaryText,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
            if (secondaryText != null) {
                Text(
                    secondaryText,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Destination<EventsGraph>(style = DestinationStyle.Dialog::class)
@Composable
fun EventDialog(
    eventId: String,
    navigator: DestinationsNavigator,
) {
    // buscar cenas
    EventDialogContent(eventName = "O Pato Lindo", navigator, eventId)
}

@Destination<EventsGraph>(style = DestinationStyle.Dialog::class)
@Composable
fun EventConfirmationDialog(
    eventId: String,
    navigator: DestinationsNavigator,
) {
    EventDialogConfirmationContent(
        eventName = "O Pato Lindo",
        eventDate = "23 de março",
        eventTime = "13:00",
        ticketQuantity = 3,
        navigator,
    )
}

@Composable
fun EventDialogConfirmationContent(
    eventName: String,
    eventDate: String,
    eventTime: String,
    ticketQuantity: Int,
    navigator: DestinationsNavigator,
) {
    AlertDialog(
        title = {
            Text(text = "Compra de Bilhetes")
        },
        text = {
            Text(
                text =
                    "Tens a certeza que queres comprar $ticketQuantity bilhete(s) para \"$eventName\" no dia $eventDate às $eventTime?\n" +
                        "Esta ação é irreversível. ",
            )
        },
        onDismissRequest = {
            navigator.navigateUp()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navigator.popBackStack(EventDestination, false)
                },
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    navigator.navigateUp()
                },
            ) {
                Text("Cancelar")
            }
        },
    )
}

@Composable
fun EventDialogContent(
    eventName: String,
    navigator: DestinationsNavigator,
    eventId: String,
) {
    AlertDialog(
        title = {
            Text(text = "Compra de Bilhetes")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Quantos bilhetes quer comprar para \"$eventName\" ?")
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp),
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Minus",
                        )
                    }
                    InputChip(
                        onClick = { },
                        // TODO MUDAR QUANTIDADE
                        label = { Text("2") },
                        selected = true,
                    )

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Plus",
                        )
                    }
                }
            }
        },
        onDismissRequest = {
            navigator.navigateUp()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navigator.navigate(EventConfirmationDialogDestination(eventId))
                },
            ) {
                Text("Continuar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    navigator.navigateUp()
                },
            ) {
                Text("Cancelar")
            }
        },
    )
}
