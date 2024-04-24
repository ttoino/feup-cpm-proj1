package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.LoadState
import pt.up.fe.cpm.tiktek.core.model.OrderItemWithModels
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.CardRow

data class Args(val qrCodeResult: String)

@Destination<RootGraph>(
    navArgs = Args::class,
)
@Composable
internal fun PurchasedProductsRoute(
    navigator: DestinationsNavigator,
    viewModel: PurchasedProductsViewModel = hiltViewModel(),
) {
    val order by viewModel.orderWithModels.collectAsStateWithLifecycle()

    PurchasedProductsScreen(
        order = order,
        onBack = { navigator.navigateUp() },
    )
}

@Composable
fun PurchasedProductsScreen(
    order: LoadState<OrderWithModels>,
    onBack: () -> Unit,
) {
    AppBarLayout(
        title = "Compra",
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        when (order) {
            is LoadState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoadState.Error -> {
                Text("Error: ${order.message}")
            }
            is LoadState.Success -> {
                order.value.items.forEach {
                    OrderItemCard(it)
                }

                Text(
                    text = "Vouchers utilizados",
                    style = MaterialTheme.typography.titleSmall,
                )

                order.value.vouchers.forEach {
                    VoucherCard(it)
                }
            }
        }
    }
}

@Composable
internal fun OrderItemCard(item: OrderItemWithModels) {
    CardRow(
        title = "${item.quantity} x ${item.item.name}",
        subtitle = (item.item.price / 100f).toString(),
        imageUrl = item.item.imageUrl,
    )
}

@Composable
fun VoucherCard(voucher: VoucherWithModels) {
    when (voucher) {
        is VoucherWithModels.Discount ->
            CardRow(
                title = "${voucher.discount}% discount",
                imageUrl = "https://st.depositphotos.com/1010613/1960/i/450/depositphotos_19607305-stock-photo-5-percent-sale-discount.jpg",
            )
        is VoucherWithModels.Free ->
            CardRow(
                title = "Free ${voucher.item.name}",
                imageUrl = voucher.item.imageUrl,
            )
    }
}
