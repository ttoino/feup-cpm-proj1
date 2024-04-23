package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.ui.Quantity
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

internal data class NavArgs(val itemId: String)

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    style = DestinationStyle.Dialog::class,
    navArgs = NavArgs::class,
)
@Composable
fun AddToCartDialog(
    navigator: DestinationsNavigator,
    viewModel: AddToCartViewModel = hiltViewModel(),
) {
    val item by viewModel.item.collectAsStateWithLifecycle()
    val success by viewModel.success.collectAsStateWithLifecycle()

    LaunchedEffect(success) {
        if (success) {
            navigator.navigateUp()
        }
    }

    item?.let {
        AddToCartDialogContent(
            item = it,
            uiState = viewModel.uiState,
            onRemove = viewModel::remove,
            onAdd = viewModel::add,
            onAddToCart = { viewModel.addToCart() },
            onCancel = { navigator.navigateUp() },
        )
    }
}

@Composable
fun AddToCartDialogContent(
    item: CafeteriaItem,
    uiState: AddToCartUiState,
    onRemove: () -> Unit,
    onAdd: () -> Unit,
    onAddToCart: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = "Compra de ${item.name}")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Escolha a quantidade de ${item.name} desejada.",
                )

                Quantity(uiState.quantity, onRemove, onAdd)
            }
        },
        onDismissRequest = onCancel,
        confirmButton = {
            TextButton(
                onClick = onAddToCart,
            ) {
                Text("Colocar no Carrinho")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel,
            ) {
                Text("Cancelar")
            }
        },
    )
}
