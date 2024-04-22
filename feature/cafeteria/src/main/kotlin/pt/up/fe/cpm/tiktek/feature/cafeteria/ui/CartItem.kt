package pt.up.fe.cpm.tiktek.feature.cafeteria.ui

import androidx.compose.runtime.Composable
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.ui.CardRow
import pt.up.fe.cpm.tiktek.core.ui.Quantity

@Composable
internal fun CartItemCard(
    item: CafeteriaItem,
    quantity: Int,
    onRemove: () -> Unit,
    onAdd: () -> Unit,
) {
    CardRow(
        title = item.name,
        subtitle = (item.price / 100f).toString(),
        imageUrl = item.imageUrl,
    ) {
        Quantity(quantity, onRemove, onAdd)
    }
}
