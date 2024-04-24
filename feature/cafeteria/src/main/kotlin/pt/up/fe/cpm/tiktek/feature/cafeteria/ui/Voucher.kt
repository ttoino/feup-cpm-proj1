package pt.up.fe.cpm.tiktek.feature.cafeteria.ui

import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import pt.up.fe.cpm.tiktek.core.ui.CardRow

@Composable
fun VoucherCard(
    voucher: VoucherWithModels,
    selected: Boolean,
    onToggle: (Boolean) -> Unit,
) {
    val modifier =
        Modifier
            .toggleable(
                value = selected,
                role = Role.Checkbox,
                onValueChange = { onToggle(it) },
            )
    val content = @Composable {
        Checkbox(
            checked = selected,
            onCheckedChange = null,
        )
    }

    when (voucher) {
        is VoucherWithModels.Discount ->
            CardRow(
                title = "${voucher.discount}% discount",
                imageUrl = "https://st.depositphotos.com/1010613/1960/i/450/depositphotos_19607305-stock-photo-5-percent-sale-discount.jpg",
                modifier = modifier,
                content = content,
            )
        is VoucherWithModels.Free ->
            CardRow(
                title = "Free ${voucher.item.name}",
                imageUrl = voucher.item.imageUrl,
                modifier = modifier,
                content = content,
            )
    }
}
