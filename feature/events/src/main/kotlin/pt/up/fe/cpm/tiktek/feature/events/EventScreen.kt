package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.ui.relativeOffset
import pt.up.fe.cpm.tiktek.core.ui.snackbar
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph

internal data class EventArgs(val eventId: String)

@Destination<EventsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    deepLinks = [
        DeepLink(uriPattern = "tiktek://$FULL_ROUTE_PLACEHOLDER"),
    ],
    navArgs = EventArgs::class,
    route = "event",
)
@Composable
internal fun EventRoute(
    navigator: DestinationsNavigator,
    viewModel: EventViewModel, // = hiltViewModel(),
) {
    val event by viewModel.event.collectAsStateWithLifecycle()
    val snackbarHostState = snackbar
    val snackbarSuccessMessage = stringResource(R.string.purchase_tickets_success)

    LaunchedEffect(viewModel.buyTicketsSuccess) {
        viewModel.buyTicketsSuccess.filter { it }.collectLatest {
            navigator.popBackStack(EventDestination, false)
            snackbarHostState.showSnackbar(snackbarSuccessMessage)
        }
    }

    event?.let {
        EventScreen(
            event = it,
            onBack = { navigator.navigateUp() },
            onSearch = {},
            onAddToCart = { navigator.navigate(EventDialogDestination(it.id)) },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EventScreen(
    event: Event,
    onBack: () -> Unit,
    onSearch: () -> Unit,
    onAddToCart: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(event.name) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onSearch) {
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
                    onClick = onAddToCart,
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(horizontal = 8.dp)
                            .relativeOffset(y = 0.5f),
                ) {
                    Icon(Icons.Default.AddShoppingCart, contentDescription = stringResource(R.string.add_to_cart_action))
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

@Destination<EventsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    style = DestinationStyle.Dialog::class,
    navArgs = EventArgs::class,
)
@Composable
fun EventDialog(
    navigator: DestinationsNavigator,
    viewModel: EventViewModel, // = hiltViewModel(),
) {
    val event by viewModel.event.collectAsStateWithLifecycle()

    event?.let {
        EventDialogContent(
            event = it,
            uiState = viewModel.eventDialogUiState,
            onBack = { navigator.navigateUp() },
            onContinue = { navigator.navigate(EventConfirmationDialogDestination(it.id)) },
            onRemove = { viewModel.removeTicket() },
            onAdd = { viewModel.addTicket() },
        )
    }
}

@Composable
fun EventDialogContent(
    event: Event,
    uiState: EventDialogUiState,
    onBack: () -> Unit,
    onContinue: () -> Unit,
    onRemove: () -> Unit,
    onAdd: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.purchase_tickets))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(stringResource(R.string.purchase_tickets_amount_info, event.name))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp),
                ) {
                    IconButton(
                        onClick = onRemove,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(R.string.remove_ticket_action),
                        )
                    }
                    InputChip(
                        onClick = { },
                        label = { Text(uiState.ticketAmount.toString()) },
                        selected = true,
                    )

                    IconButton(
                        onClick = onAdd,
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add_ticket_action),
                        )
                    }
                }
            }
        },
        onDismissRequest = onBack,
        confirmButton = {
            TextButton(
                onClick = onContinue,
            ) {
                Text(stringResource(R.string.continue_action))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onBack,
            ) {
                Text(stringResource(R.string.cancel_action))
            }
        },
    )
}

@Destination<EventsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    style = DestinationStyle.Dialog::class,
    navArgs = EventArgs::class,
)
@Composable
fun EventConfirmationDialog(
    navigator: DestinationsNavigator,
    viewModel: EventViewModel, // = hiltViewModel(),
) {
    val event by viewModel.event.collectAsStateWithLifecycle()

    event?.let {
        EventDialogConfirmationContent(
            it,
            viewModel.eventDialogUiState,
            onBack = { navigator.navigateUp() },
            onConfirm = { viewModel.buyTickets() },
        )
    }
}

@Composable
fun EventDialogConfirmationContent(
    event: Event,
    uiState: EventDialogUiState,
    onBack: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(stringResource(R.string.purchase_tickets))
        },
        text = {
            Text(
                pluralStringResource(
                    R.plurals.purchase_tickets_confirmation_info,
                    uiState.ticketAmount,
                    uiState.ticketAmount,
                    event.name,
                    event.date.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
                    event.startTime.toMillisecondOfDay().toLong(),
                ),
            )
        },
        onDismissRequest = onBack,
        confirmButton = {
            TextButton(
                enabled = !uiState.loading,
                onClick = onConfirm,
            ) {
                Text(stringResource(R.string.confirm_action))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onBack,
            ) {
                Text(stringResource(R.string.cancel_action))
            }
        },
    )
}
