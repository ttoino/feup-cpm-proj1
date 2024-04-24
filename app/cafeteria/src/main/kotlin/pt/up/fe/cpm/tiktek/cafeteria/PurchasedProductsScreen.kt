package pt.up.fe.cpm.tiktek.cafeteria

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import pt.up.fe.cpm.tiktek.core.ui.snackbar

@Destination<RootGraph>()
@Composable
internal fun PurchasedProductsRoute(qrCodeResult: String) {
    PurchasedProductsScreen(qrCodeResult)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasedProductsScreen(qrCodeResult: String) {
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val viewModel: PurchasedProductsViewModel = hiltViewModel()

    val result by viewModel.orderWithModels.collectAsStateWithLifecycle()
    Log.d("resultpurchase", "Result of purchase products: $result")

    val snackbarHostState = snackbar

    LaunchedEffect(viewModel.orderWithModels) {
        viewModel.orderWithModels.collect {
            it?.let {
                snackbarHostState.showSnackbar(
                    message = "Comprado com sucesso!",
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {
                    Text(
                        text = "Os meus produtos adquiridos",
                    )
                },
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            // Check if the result is null
            if (result == null) {
                // Display a message for unsuccessful purchase
                Text(
                    text = "Purchase unsuccessful",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp),
                )
            } else {
                LazyColumn(
                    modifier =
                        Modifier
                            .padding(it)
                            .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    item {
                        Text(
                            text = "Número de pedido: 2", // ${result?.id}",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                    val orderProducts = result!!.items
                    val orderVouchers = result!!.vouchers

                    items(orderProducts.size) { index ->
                        val product = orderProducts.get(index)
                        PurchasedProduct(
                            cafetariaItem = product.item.name,
                            quantity = product.quantity,
                        )
                    }

                    item {
                        Text(
                            text = "Vouchers utilizados",
                            fontSize = 25.sp,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }

                    items(orderVouchers.size) { index ->
                        val voucher = orderVouchers.get(index)
                        Voucher(
                            voucherName = voucher.userId,
                            voucherQuantity = 5, // TODO: MUDARRRRRRRRRRRRRRRRRRRRRRR
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PurchasedProduct(
    cafetariaItem: String,
    quantity: Int,
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
                    onClick = { },
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
                    text = "$quantity x $cafetariaItem",
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun Voucher(
    voucherName: String,
    voucherQuantity: Int,
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
                    onClick = { },
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
                    text = voucherName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    // TODO: Change to a function that formats the money
                    text = "Quantidade utilizada: $voucherQuantity",
                    fontSize = 15.sp,
                )
            }
        }
    }
}
