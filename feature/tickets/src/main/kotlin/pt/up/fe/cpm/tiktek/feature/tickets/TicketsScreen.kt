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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import pt.up.fe.cpm.tiktek.feature.tickets.navigation.TicketsGraph

@Destination<TicketsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun TicketsRoute() {
    // TODO: Get data

    TicketsScreen()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TicketsScreen() {
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
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = "As minhas compras",
                    )
                },
            )
        },
    ) {
        Column(
            modifier =
                Modifier
                    .padding(it)
                    .verticalScroll(rememberScrollState()),
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
                    EventTicket(
                        eventImageLink = "https://cdn-images.rtp.pt/icm/noticias/images/70/702cd1ace0f478720fcc814e78366ef4?w=860&q=90&rect=0,0,1024,561",
                        eventName = "Hamilton Infantil",
                        ticketStatus = true,
                    )
                    EventTicket(
                        eventImageLink = "https://images-na.ssl-images-amazon.com/images/S/compressed.photo.goodreads.com/books/1379865124i/11347141.jpg",
                        eventName = "O pequeno mundo de Teresa",
                        ticketStatus = true,
                    )
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
                                contentDescription = "Ver histórico de compras",
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(
                                modifier = Modifier.width(8.dp),
                            )
                            Text(text = "Consultar histórico de compras")
                        }
                    }
                }
            } else if (selectedTabIndex == 1) {
                Column(
                    modifier =
                        Modifier
                            .padding(vertical = 16.dp),
                ) {
                    EventTicket(
                        eventImageLink = "https://i.pinimg.com/564x/c9/c3/3a/c9c33a1344689e3dff43e51dddb572ce.jpg",
                        eventName = "Café",
                        ticketStatus = true,
                    )
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
                            contentDescription = "Ver histórico de compras",
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(
                            modifier = Modifier.width(8.dp),
                        )
                        Text(text = "Consultar histórico de compras")
                    }
                }
            }
        }
    }
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
)

@Composable
private fun EventTicket(
    eventImageLink: String,
    eventName: String,
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
                    text = "Por usar\nVálido até [data]",
                    fontSize = 15.sp,
                    color = Color(0xffb2f98a),
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
