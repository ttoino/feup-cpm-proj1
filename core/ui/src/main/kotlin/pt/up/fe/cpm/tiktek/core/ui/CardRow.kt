package pt.up.fe.cpm.tiktek.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CardRow(
    title: String,
    subtitle: String,
    imageUrl: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        CardRowContent(title, subtitle, imageUrl, content)
    }
}

@Composable
fun CardRow(
    title: String,
    subtitle: String,
    imageUrl: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {},
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        CardRowContent(title, subtitle, imageUrl, content)
    }
}

@Composable
private fun CardRowContent(
    title: String,
    subtitle: String,
    imageUrl: String,
    content: @Composable () -> Unit = {},
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(Modifier.fillMaxWidth())

        content()

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(80.dp),
        )
    }
}
