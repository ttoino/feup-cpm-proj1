package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    EventsScreen(navigator)
    /*Button(
        onClick = { navigator.navigate(EventDestination("")) },
        modifier =
            Modifier
                .offset(y = 300.dp),
    ) {
        Text("Event")
    }*/
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun EventsScreen(navigator: DestinationsNavigator) {
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = "Eventos",
                    )
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
            // verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
            ) {
                for (category in getAllEventCategories()) {
                    FilterEventChip(
                        categoryName = category.value,
                        iconImageVector = category.icon,
                        iconContentDescription = category.contentDescription,
                    )
                }
            }
            Text(
                text = "Recomendados",
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                modifier =
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 16.dp),
            ) {
                RecommendedEvent(
                    eventImageLink = "https://i.pinimg.com/originals/ee/78/c6/ee78c67c41f6439bb9ce406907c91f3d.jpg",
                    eventName = "O Pato Lindo",
                    eventDate = "20 de Fevereiro",
                    eventTime = "12:30",
                    navigator = navigator,
                )
                RecommendedEvent(
                    eventImageLink = "https://parade.com/.image/t_share/MjAzMzU3NzQxMzU4NTIzOTgz/happy-birthday-wishes-messages.jpg",
                    eventName = "Aniversário",
                    eventDate = "2 de Abril",
                    eventTime = "20:30",
                    navigator = navigator,
                )
                RecommendedEvent(
                    eventImageLink = "https://i.pinimg.com/564x/b8/85/4c/b8854cfb077f5e7f6646899455f27704.jpg",
                    eventName = "Fada Julia - Um momento bom",
                    eventDate = "10 de Abril",
                    eventTime = "14:45",
                    navigator = navigator,
                )
                RecommendedEvent(
                    eventImageLink =
                        "https://s.calendarr.com/upload/datas/di/ai/" +
                            "dia-internacional-da-mulher_c.jpg?auto_optimize=low&width=640",
                    eventName = "Dia das mulheres",
                    eventDate = "8 de Março",
                    eventTime = "14:45",
                    navigator = navigator,
                )
            }
            Text(
                text = "Hoje",
                style = MaterialTheme.typography.headlineSmall,
            )
            Column(
                modifier =
                    Modifier
                        .padding(vertical = 16.dp),
            ) {
                TodayEvent(
                    eventImageLink =
                        "https://cdn-images.rtp.pt/icm/noticias/images/70/" +
                            "702cd1ace0f478720fcc814e78366ef4?w=860&q=90&rect=0,0,1024,561",
                    eventName = "Hamilton Infantil",
                    eventDate = "20 de Fevereiro",
                    eventTime = "12:30",
                    navigator = navigator,
                )
                TodayEvent(
                    eventImageLink =
                        "https://images-na.ssl-images-amazon.com/images/S/" +
                            "compressed.photo.goodreads.com/books/1379865124i/11347141.jpg",
                    eventName = "O pequeno mundo de Teresa",
                    eventDate = "20 de Fevereiro",
                    eventTime = "12:30",
                    navigator = navigator,
                )
                TodayEvent(
                    eventImageLink = "https://i.pinimg.com/originals/ee/78/c6/ee78c67c41f6439bb9ce406907c91f3d.jpg",
                    eventName = "O Pato Lindo",
                    eventDate = "20 de Fevereiro",
                    eventTime = "12:30",
                    navigator = navigator,
                )
            }
        }
    }
}

//  ================ Filter events section  ================
@Composable
private fun FilterEventChip(
    categoryName: String,
    iconImageVector: ImageVector,
    iconContentDescription: String,
) {
    var selected by remember { mutableStateOf(false) }
    FilterChip(
        onClick = { selected = !selected },
        label = {
            Text(text = categoryName)
        },
        modifier = Modifier.padding(5.dp),
        selected = selected,
        leadingIcon =
            if (selected) {
                {
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = "Done icon",
                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                    )
                }
            } else {
                {
                    Icon(
                        imageVector = iconImageVector,
                        contentDescription = iconContentDescription,
                        modifier = Modifier.size(FilterChipDefaults.IconSize),
                    )
                }
            },
    )
}

enum class EventCategory(val value: String, val icon: ImageVector, val contentDescription: String) {
    TEATRO("Teatro", Icons.Filled.TheaterComedy, "Theater category icon"),
    MUSICAL("Musical", Icons.Filled.MusicNote, "Musical category icon"),
    FILME("Filme", Icons.Filled.Movie, "Movie category icon"),
    WI("Filme", Icons.Filled.Movie, "Movie category icon"),
    WU("Filme", Icons.Filled.Movie, "Movie category icon"),
}

fun getAllEventCategories(): List<EventCategory> {
    return listOf(EventCategory.TEATRO, EventCategory.MUSICAL, EventCategory.FILME, EventCategory.WI, EventCategory.WU)
}

/**
 * Retrieves the [EventCategory] based on the provided value.
 * Returns null if no matching category is found.
 */
fun getEventCategory(value: String): EventCategory? {
    val map = EventCategory.values().associateBy(EventCategory::value)
    return map[value]
}

// =============== Show recommended events section =================

// TODO: make it clickable and open event page
@Composable
private fun RecommendedEvent(
    eventImageLink: String,
    eventName: String,
    eventDate: String,
    eventTime: String,
    navigator: DestinationsNavigator,
) {
    Column(
        modifier =
            Modifier
                .size(width = 150.dp, height = 220.dp)
                .padding(10.dp)
                .clickable(
                    onClick = { navigator.navigate(EventDestination("")) },
                ),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
        ) {
            AsyncImage(
                model = eventImageLink,
                contentDescription = "Event Image",
                // Maintain aspect ratio and crop if necessary
                contentScale = ContentScale.Crop,
                // Fill the available space within the Box
                modifier = Modifier.fillMaxSize(),
            )
        }
        Text(
            text = eventName,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "$eventDate | $eventTime",
            fontSize = 15.sp,
        )
    }
}

// =============== Show today events section =================

// TODO: organize layout
@Composable
private fun TodayEvent(
    eventImageLink: String,
    eventName: String,
    eventDate: String,
    eventTime: String,
    navigator: DestinationsNavigator,
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
                .padding(5.dp)
                .clickable(
                    onClick = { navigator.navigate(EventDestination("")) },
                ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(
                    text = eventName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "$eventDate | $eventTime",
                    fontSize = 15.sp,
                )
            }
            AsyncImage(
                model = eventImageLink,
                contentDescription = "Event Image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(80.dp),
            )
        }
    }
}
