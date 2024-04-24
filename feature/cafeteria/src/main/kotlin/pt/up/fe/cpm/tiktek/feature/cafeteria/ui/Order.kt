package pt.up.fe.cpm.tiktek.feature.cafeteria.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels

@Composable
fun OrderCard(order: OrderWithModels) {
    OutlinedCard {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Order ${order.id}",
                style = MaterialTheme.typography.headlineSmall,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            ProvideTextStyle(MaterialTheme.typography.bodySmall) {
                Column {
                    order.items.forEach {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text("${it.quantity}x ${it.item.name}")
                            Text("€${it.item.price / 100f}")
                        }
                    }
                }
            }

            HorizontalDivider()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Subtotal")
                Text("€${order.subtotal / 100f}")
            }

            ProvideTextStyle(MaterialTheme.typography.bodySmall) {
                Column {
                    order.vouchers.forEach {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            when (it) {
                                is VoucherWithModels.Free -> {
                                    Text("Free ${it.item.name}")
                                    Text("-€${it.item.price / 100f}")
                                }

                                is VoucherWithModels.Discount -> {
                                    Text("${it.discount}% discount")
                                    Text("-${it.discount}%")
                                }
                            }
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("Total")
                Text("€${order.total / 100f}")
            }
        }
    }
}
