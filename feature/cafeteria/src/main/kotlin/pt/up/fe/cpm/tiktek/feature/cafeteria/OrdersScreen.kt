package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph
import pt.up.fe.cpm.tiktek.feature.cafeteria.ui.OrderCard

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
fun OrdersRoute(
    navigator: DestinationsNavigator,
    viewModel: OrdersViewModel = hiltViewModel(),
) {
    val orders by viewModel.orders.collectAsStateWithLifecycle()

    OrdersScreen(
        orders = orders,
        onBack = { navigator.navigateUp() },
    )
}

@Composable
fun OrdersScreen(
    orders: List<OrderWithModels>,
    onBack: () -> Unit,
) {
    AppBarLayout(
        title = "Orders",
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        orders.forEach {
            OrderCard(it)
        }
    }
}
