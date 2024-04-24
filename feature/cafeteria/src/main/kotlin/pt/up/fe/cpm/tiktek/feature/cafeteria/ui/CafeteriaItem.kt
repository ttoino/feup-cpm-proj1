package pt.up.fe.cpm.tiktek.feature.cafeteria.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem

@Composable
internal fun CafeteriaItemCard(
    item: CafeteriaItem,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        AsyncImage(
            model = item.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().aspectRatio(5f / 3f),
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyLarge,
                )

                Text(
                    text = "€${item.price / 100f}",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

            Spacer(Modifier.defaultMinSize(minWidth = 8.dp).weight(1f))

            FloatingActionButton(
                onClick = onAddToCart,
                modifier = Modifier.align(Alignment.Bottom),
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to cart")
            }
        }
    }
}
