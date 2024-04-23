package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.Icon
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
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph
import pt.up.fe.cpm.tiktek.feature.cafeteria.ui.VoucherCard

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    route = "vouchers",
)
@Composable
internal fun VouchersRoute(
    navigator: DestinationsNavigator,
    viewModel: VouchersViewModel = hiltViewModel(),
) {
    val vouchers by viewModel.vouchers.collectAsStateWithLifecycle()

    VouchersScreen(
        vouchers = vouchers,
        onBack = { navigator.navigateUp() },
        onToggleVoucher = { voucherId, selected ->
            if (selected) {
                viewModel.addVoucher(voucherId)
            } else {
                viewModel.removeVoucher(voucherId)
            }
        },
    )
}

@Composable
internal fun VouchersScreen(
    vouchers: List<Pair<VoucherWithModels, Boolean>>,
    onBack: () -> Unit,
    onToggleVoucher: (String, Boolean) -> Unit,
) {
    AppBarLayout(
        title = "Vouchers",
        onBack = onBack,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        if (vouchers.isEmpty()) {
            NoVouchers()
        }

        vouchers.forEach { (voucher, selected) ->
            VoucherCard(voucher, selected, onToggle = { onToggleVoucher(voucher.id, it) })
        }
    }
}

@Composable
internal fun NoVouchers() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Icon(
            imageVector = Icons.Default.SentimentVeryDissatisfied,
            contentDescription = "Sadge",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(154.dp),
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text("Não há vouchers disponíveis", style = MaterialTheme.typography.titleLarge)
    }
}
