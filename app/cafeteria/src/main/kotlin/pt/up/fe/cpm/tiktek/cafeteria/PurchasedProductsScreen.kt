package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PurchasedProductsScreen(purchase: JSONObject) {
    var scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val orderNumber = purchase.getInt("orderNumber")
    val orderProducts = purchase.getJSONArray("orderProducts")
    val orderVouchers = purchase.getJSONArray("orderVouchers")
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
        LazyColumn(
            modifier = Modifier.padding(it).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {
                Text(
                    text = "Número de pedido: $orderNumber",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }

            items(orderProducts.length()) { index ->
                val product = orderProducts.getJSONObject(index)
                PurchasedProduct(
                    cafetariaItem = product.getString("cafetariaItem"),
                    quantity = product.getInt("quantity"),
                    totalPrice = product.getInt("totalPrice"),
                )
            }

            item {
                Text(
                    text = "Vouchers utilizados",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
            }

            items(orderVouchers.length()) { index ->
                val voucher = orderVouchers.getJSONObject(index)
                Voucher(
                    voucherName = voucher.getString("voucherName"),
                    voucherQuantity = voucher.getInt("voucherQuantity"),
                )
            }

            item {
                Button(
                    onClick = { /*TODO*/ },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.TaskAlt,
                        contentDescription = "Validar bilhete",
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(
                        modifier = Modifier.width(8.dp),
                    )
                    Text(text = "Recolher compra")
                }
            }
        }
    }
}

@Composable
private fun PurchasedProduct(
    cafetariaItem: String,
    quantity: Int,
    totalPrice: Int,
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
                Text(
                    text = "$totalPrice.00€", // TODO: Change to a function that formats the money
                    fontSize = 15.sp,
                )
            }
        }
    }
}

@Composable
private fun Voucher(
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
                    text = "Quantidade utilizada: $voucherQuantity", // TODO: Change to a function that formats the money
                    fontSize = 15.sp,
                )
            }
        }
    }
}
