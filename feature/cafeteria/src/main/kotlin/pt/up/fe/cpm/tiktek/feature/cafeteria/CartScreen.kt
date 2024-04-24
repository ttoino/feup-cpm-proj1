package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartConfirmDialogDestination
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartScanRouteDestination
import com.ramcosta.composedestinations.generated.cafeteria.destinations.VouchersDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import pt.up.fe.cpm.tiktek.core.model.CartWithModels
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.BiometricPrompt
import pt.up.fe.cpm.tiktek.core.ui.getActivity
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph
import pt.up.fe.cpm.tiktek.feature.cafeteria.ui.CartItemCard

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CartRoute(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel,
) {
    val activity = LocalContext.current.getActivity()

    val cart by viewModel.cart.collectAsStateWithLifecycle()
    val biometricResult by viewModel.biometricResult.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(biometricResult) {
        when (biometricResult) {
            BiometricPrompt.BiometricResult.AuthenticationSuccess -> navigator.navigate(CartConfirmDialogDestination())
            else -> Unit
        }
    }

    CartScreen(
        cart = cart,
        onBack = { navigator.navigateUp() },
        onRemove = viewModel::removeItem,
        onAdd = viewModel::addItem,
        onVouchers = { navigator.navigate(VouchersDestination()) },
        onCancel = { navigator.navigateUp() },
        onBuy = { viewModel.bioAuth(activity!!) },
    )
}

@Composable
internal fun CartScreen(
    cart: CartWithModels,
    onBack: () -> Unit,
    onRemove: (String) -> Unit,
    onAdd: (String) -> Unit,
    onVouchers: () -> Unit,
    onCancel: () -> Unit,
    onBuy: () -> Unit,
) {
    AppBarLayout(
        title = stringResource(R.string.cart_title),
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        cart.items.forEach { (item, quantity) ->
            CartItemCard(
                item,
                quantity,
                onRemove = { onRemove(item.id) },
                onAdd = { onAdd(item.id) },
            )
        }

        HorizontalDivider(thickness = 4.dp, color = MaterialTheme.colorScheme.primaryContainer)

        Button(
            onClick = onVouchers,
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(),
        ) {
            Text(text = stringResource(R.string.button_voucher))
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(R.string.price_to_pay),
                fontWeight = FontWeight.Bold,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                fontSize = 25.sp,
            )

            Text(
                text = "${cart.total / 100f}€",
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                textAlign = TextAlign.End,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                onClick = onCancel,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
                colors =
                    ButtonDefaults.buttonColors(
                        MaterialTheme.colorScheme.error,
                        MaterialTheme.colorScheme.onError,
                    ),
            ) {
                Text(text = stringResource(R.string.button_canel))
            }

            Button(
                onClick = onBuy,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
            ) {
                Text(text = stringResource(R.string.button_confirm))
            }
        }
    }
}

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    style = DestinationStyle.Dialog::class,
)
@Composable
fun CartConfirmDialog(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel,
) {
    val cart by viewModel.cart.collectAsStateWithLifecycle()

    CartConfirmDialogContent(
        cart = cart,
        onConfirm = { navigator.navigate(CartScanRouteDestination()) },
        onBack = { navigator.navigateUp() },
    )
}

@Composable
fun CartConfirmDialogContent(
    cart: CartWithModels,
    onConfirm: () -> Unit,
    onBack: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.button_confirm))
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = stringResource(R.string.products),
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )

                cart.items.forEach { (item, quantity) ->
                    Text(text = "- $quantity ${item.name}")
                }

                Spacer(
                    modifier = Modifier.width(16.dp),
                )
                HorizontalDivider()

                Spacer(
                    modifier = Modifier.width(16.dp),
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = stringResource(R.string.price),
                        fontWeight = FontWeight.Bold,
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "${cart.total / 100f}€",
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.End,
                    )
                }
            }
        },
        onDismissRequest = onBack,
        confirmButton = {
            TextButton(
                onClick = onConfirm,
            ) {
                Text(stringResource(R.string.button_confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onBack,
            ) {
                Text(stringResource(R.string.button_canel))
            }
        },
    )
}
