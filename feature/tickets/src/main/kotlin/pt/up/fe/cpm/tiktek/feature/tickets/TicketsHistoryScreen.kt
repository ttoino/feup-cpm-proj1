package pt.up.fe.cpm.tiktek.feature.tickets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.theme.TikTekTheme
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.TicketsGraph

@Destination<TicketsGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun TicketsHistoryRoute(
    navigator: DestinationsNavigator,
    viewModel: TicketsViewModel = hiltViewModel(),
) {
    val tickets by viewModel.tickets.collectAsStateWithLifecycle()
    TicketsHistoryScreen(
        tickets = tickets,
        navigator,
        onBack = { navigator.navigateUp() },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsHistoryScreen(
    tickets: List<TicketWithEvent>,
    navigator: DestinationsNavigator,
    onBack: () -> Unit,
) {
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var tabItems =
        listOf(
            TabItem(
                title = "Espetáculo",
                unselectedIcon = Icons.Outlined.TheaterComedy,
                selectedIcon = Icons.Filled.TheaterComedy,
            ),
            TabItem(
                title = "Cafetaria",
                unselectedIcon = Icons.Outlined.LocalCafe,
                selectedIcon = Icons.Filled.LocalCafe,
            ),
        )
    var selectedTabIndex by remember {
        mutableIntStateOf(0)
    }
    AppBarLayout(
        title = "Histórico de compras",
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = {
                            Text(text = item.title)
                        },
                        icon = {
                            Icon(
                                imageVector =
                                    if (index == selectedTabIndex) {
                                        item.selectedIcon
                                    } else {
                                        item.unselectedIcon
                                    },
                                contentDescription = item.title,
                            )
                        },
                    )
                }
            }
        }
        if (selectedTabIndex == 0) {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 16.dp),
            ) {
                for (ticket in tickets) {
                    if (ticket.event.date.atTime(ticket.event.endTime).compareTo(
                            Clock.System.now().toLocalDateTime(
                                TimeZone.UTC,
                            ),
                        ) < 0
                    ) {
                        EventTicket(
                            eventImageLink = ticket.event.imageUrl,
                            eventName = ticket.event.name,
                            ticketSeat = ticket.seat,
                            eventDate = ticket.event.date,
                        )
                    }
                }
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 50.dp),
                ) {
                    Button(
                        onClick = { navigator.navigateUp() },
                        modifier =
                            Modifier.align(
                                Alignment.Center,
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.History,
                            contentDescription = "Ver bilhetes ativos",
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp),
                        )
                        Text(text = "Ver bilhetes ativos")
                    }
                }
            }
        } else if (selectedTabIndex == 1) {
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 16.dp),
            ) {
                // TODO TICKETS DE CAFETERIA
            }

            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 50.dp),
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier =
                        Modifier.align(
                            Alignment.Center,
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.History,
                        contentDescription = "Ver pedidos ativos",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp),
                    )
                    Text(text = "Ver pedidos ativos")
                }
            }
        }
    }
}

@Composable
private fun EventTicket(
    eventImageLink: String,
    eventName: String,
    ticketSeat: String,
    eventDate: LocalDate,
) {
    Card(
        border =
            BorderStroke(
                2.dp,
                MaterialTheme.colorScheme.outlineVariant,
            ),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
        modifier =
            Modifier
                .padding(5.dp),
        /*.clickable(
            onClick = { navigator.navigate(EventDestination("")) }
        )*/
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier =
                    Modifier
                        .padding(16.dp),
            ) {
                Text(
                    text = eventName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "Seat $ticketSeat \nVálido até $eventDate",
                    fontSize = 15.sp,
                    color = TikTekTheme.extendedColorScheme.success,
                )
            }
            AsyncImage(
                model = eventImageLink,
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(105.dp),
            )
        }
    }
}
