package pt.up.fe.cpm.tiktek.feature.events

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.feature.events.navigation.EventsGraph
import pt.up.fe.cpm.tiktek.feature.events.ui.EventColumn
import pt.up.fe.cpm.tiktek.feature.events.ui.EventRow

@Destination<EventsGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun EventsRoute(
    navigator: DestinationsNavigator,
    viewModel: EventsViewModel = hiltViewModel(),
) {
    val events by viewModel.events.collectAsStateWithLifecycle()

    EventsScreen(
        events = events,
        onNavigateToEvent = { navigator.navigate(EventDestination(it)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun EventsScreen(
    events: List<Event>,
    onNavigateToEvent: (String) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(stringResource(R.string.events))
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.Search, contentDescription = stringResource(R.string.search_action))
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
                horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                text = stringResource(R.string.recommended),
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier =
                    Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 16.dp),
            ) {
                events.take(10).forEach { EventColumn(it, onNavigateToEvent) }
            }
            Text(
                text = stringResource(R.string.today),
                style = MaterialTheme.typography.headlineSmall,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier =
                    Modifier
                        .padding(vertical = 16.dp),
            ) {
                events.take(10).forEach { EventRow(it, onNavigateToEvent) }
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
