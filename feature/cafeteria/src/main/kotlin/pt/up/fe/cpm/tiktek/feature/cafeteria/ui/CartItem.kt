package pt.up.fe.cpm.tiktek.feature.cafeteria.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
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
        subtitle = "€${item.price / 100f}",
        imageUrl = item.imageUrl,
    ) {
        Quantity(quantity, onRemove, onAdd)
    }
}

@Preview
@Composable
private fun CartItemCardPreview() {
    CartItemCard(
        item =
            CafeteriaItem(
                id = "1",
                name = "Item a;mlkasdmla smdlkamslkd maklsdma lkmsdlk amsd amlsdka mskldma lksmdla mslkdm klas",
                price = 100,
                imageUrl = "https://example.com/image.jpg",
            ),
        quantity = 1,
        onRemove = {},
        onAdd = {},
    )
}
