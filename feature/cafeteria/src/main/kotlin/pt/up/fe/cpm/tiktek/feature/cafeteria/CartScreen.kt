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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CafeteriaBuyDialogDestination
import com.ramcosta.composedestinations.generated.cafeteria.destinations.VouchersDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import pt.up.fe.cpm.tiktek.core.model.CartWithModels
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.BiometricPrompt
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph
import pt.up.fe.cpm.tiktek.feature.cafeteria.ui.CartItemCard

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CartRoute(
    navigator: DestinationsNavigator,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val cart by viewModel.cart.collectAsStateWithLifecycle()
    val biometricResult by viewModel.biometricResult.collectAsStateWithLifecycle(initialValue = null)

    CartScreen(
        cart = cart,
        onBack = { navigator.navigateUp() },
        onRemove = viewModel::removeItem,
        onAdd = viewModel::addItem,
        onVouchers = { navigator.navigate(VouchersDestination()) },
        onCancel = { navigator.navigateUp() },
        onBuy = { navigator.navigate(CafeteriaBuyDialogDestination()) },
    )
}

val promptManager by lazy {
    BiometricPrompt(this)
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
        title = "Carrinho",
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

        // ------------- fim dos items --------------------

        HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.primaryContainer)

        Button(
            onClick = onVouchers,
            modifier =
                Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 40.dp)
                    .fillMaxWidth(),
        ) {
            Text(text = "Adicionar voucher")
        }

        // Valor a pagar

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Preço a pagar",
                fontWeight = FontWeight.Bold,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                fontSize = 25.sp,
            )

            Text(
                text = "10.9€",
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .fillMaxWidth(),
                textAlign = TextAlign.End,
            ) // TODO MUDAR ISTO
        }
        // Botões cancelar ou prodceder à compra

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Button(
                // TODO APAGAR TUDO OQ TA NO CARRINHO
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
                Text(text = "Cancelar")
            }

            Button(
                // TODO PROCESSO DE COMPRA
                onClick = onBuy,
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .padding(vertical = 20.dp)
                        .fillMaxWidth(),
//                    colors = ButtonDefaults.buttonColors(Color(0xFFD0BCFF)),
            ) {
                Text(text = "Efetuar compra")
            }
        }
    }
}

@Destination<CafeteriaGraph>(style = DestinationStyle.Dialog::class)
@Composable
fun CafeteriaBuyDialog(
//    orderId: String,
    navigator: DestinationsNavigator,
) {
    CafeteriaBuyDialogContent(
//        orderId,
        foodItems =
            arrayOf(
                Food("Apple", 5),
                Food("Banana", 3),
                Food("Orange", 2),
            ),
        navigator,
    )
}

data class Food(val name: String, val quantity: Int)

@Composable
fun CafeteriaBuyDialogContent(
//    orderId: String,
    foodItems: Array<Food>,
    navigator: DestinationsNavigator,
) {
    AlertDialog(
        title = {
            Text(text = "Confirmar Compra")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Produtos na lista",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                )

                for (food in foodItems) {
                    if (food.quantity > 0) {
                        Text(text = "- ${food.quantity} ${food.name}")
                    }
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
                        text = "Preço",
                        fontWeight = FontWeight.Bold,
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        fontSize = 20.sp,
                    )

                    Text(
                        text = "10.9€",
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                        fontSize = 17.sp,
                        textAlign = TextAlign.End,
                    ) // TODO MUDAR ISTO
                }
            }
        },
        onDismissRequest = {
            navigator.navigateUp()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    navigator.navigateUp()
                },
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    navigator.navigateUp()
                },
            ) {
                Text("Cancelar")
            }
        },
    )
}
/*

biometricResult?.let { result ->
    Text(
        text =
        when (result) {
            is BiometricPrompt.BiometricResult.AuthenticationError -> {
                result.error
            }
            BiometricPrompt.BiometricResult.AuthenticationFailed -> {
                "Authentication Failed"
            }
            BiometricPrompt.BiometricResult.AuthenticationNotSet -> {
                "Authentication not set"
            }
            BiometricPrompt.BiometricResult.AuthenticationSuccess -> {
                "Authentication success"
            }
            BiometricPrompt.BiometricResult.FeatureUnavailable -> {
                "Feature Unavailable"
            }
            BiometricPrompt.BiometricResult.HardwareUnavailable -> {
                "Hardware Unavailable"
            }
        },
*/
