package pt.up.fe.cpm.tiktek.feature.events.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import pt.up.fe.cpm.tiktek.core.model.Event
import pt.up.fe.cpm.tiktek.core.ui.CardRow
import pt.up.fe.cpm.tiktek.feature.events.R

@Composable
internal fun EventColumn(
    event: Event,
    onNavigateToEvent: (String) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
        onClick = { onNavigateToEvent(event.id) },
        modifier = Modifier.width(144.dp),
    ) {
        AsyncImage(
            model = event.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium),
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = event.name,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text =
                stringResource(
                    R.string.date_time_value,
                    event.date.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
                    event.startTime.toMillisecondOfDay().toLong(),
                ),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
internal fun EventRow(
    event: Event,
    onNavigateToEvent: (String) -> Unit,
) {
    CardRow(
        title = event.name,
        subtitle =
            stringResource(
                R.string.date_time_value,
                event.date.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds(),
                event.startTime.toMillisecondOfDay().toLong(),
            ),
        imageUrl = event.imageUrl,
        onClick = { onNavigateToEvent(event.id) },
    )
}
