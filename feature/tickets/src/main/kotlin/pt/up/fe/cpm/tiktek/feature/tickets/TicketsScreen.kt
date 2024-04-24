package pt.up.fe.cpm.tiktek.feature.tickets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.TheaterComedy
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.tickets.destinations.TicketScanRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.TicketsGraph
import pt.up.fe.cpm.tiktek.feature.tickets.ui.TicketCard

@Destination<TicketsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun TicketsRoute(
    navigator: DestinationsNavigator,
    viewModel: TicketsViewModel = hiltViewModel(),
) {
    val availableTickets by viewModel.availableTickets.collectAsStateWithLifecycle()
    val usedTickets by viewModel.usedTickets.collectAsStateWithLifecycle()

    TicketsScreen(
        availableTickets = availableTickets,
        usedTickets = usedTickets,
        onClickTicket = { navigator.navigate(TicketScanRouteDestination(it)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsScreen(
    availableTickets: List<TicketWithEvent>,
    usedTickets: List<TicketWithEvent>,
    onClickTicket: (String) -> Unit,
) {
    val tabItems =
        listOf(
            TabItem(
                title = stringResource(R.string.available),
                unselectedIcon = Icons.Outlined.TheaterComedy,
                selectedIcon = Icons.Filled.TheaterComedy,
            ),
            TabItem(
                title = stringResource(R.string.used),
                unselectedIcon = Icons.Outlined.History,
                selectedIcon = Icons.Filled.History,
            ),
        )

    var isHistory by remember { mutableStateOf(false) }

    AppBarLayout(
        title = stringResource(R.string.tickets_title),
    ) {
        PrimaryTabRow(
            selectedTabIndex = if (isHistory) 1 else 0,
        ) {
            tabItems.forEachIndexed { index, item ->
                val selected = index == if (isHistory) 1 else 0
                Tab(
                    selected = selected,
                    onClick = {
                        isHistory = index == 1
                    },
                    text = {
                        Text(text = item.title)
                    },
                    icon = {
                        Icon(
                            imageVector =
                                if (selected) {
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

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            if (!isHistory) {
                availableTickets
            } else {
                usedTickets
            }.forEach {
                TicketCard(
                    ticket = it,
                    onClick = { onClickTicket(it.id) },
                )
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)
