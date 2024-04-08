package pt.up.fe.cpm.tiktek.feature.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.annotation.parameters.DeepLink
import com.ramcosta.composedestinations.annotation.parameters.FULL_ROUTE_PLACEHOLDER
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
    deepLinks = [
        DeepLink(uriPattern = "tiktek://$FULL_ROUTE_PLACEHOLDER"),
    ],
    route = "vouchers",
)
@Composable
internal fun VouchersRoute(navigator: DestinationsNavigator) {
    VouchersScreen(navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun VouchersScreen(navigator: DestinationsNavigator) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text("Vouchers") },
                navigationIcon = {
                    IconButton(
                        onClick = { navigator.navigateUp() },
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
        ) {
            VouchersCard(
                voucherName = "☕  Café Grátis!",
                voucherImg = "https://i.pinimg.com/564x/37/ae/2b/37ae2b49e4a4061272e8dd6fde5a210d.jpg",
                voucherQuantity = 2,
                voucherDescription = "Este voucher equivale a um café grátis.",
                isUsed = false,
            )
        }
    }
}

@Composable
internal fun VouchersCard(
    voucherName: String,
    voucherImg: String,
    voucherQuantity: Int,
    voucherDescription: String,
    isUsed: Boolean,
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
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            AsyncImage(
                model = voucherImg,
                contentDescription = "Voucher Image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(80.dp),
            )
            Column(
                modifier =
                    Modifier.padding(16.dp)
                        .fillMaxHeight(),
            ) {
                Text(
                    text = voucherName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer to push text to bottom
                Text(
                    text = "$voucherDescription",
                    fontSize = 15.sp,
                )
            }
            Column {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp),
                ) {
                }
            }
            Column {
                Checkbox(
                    checked = isUsed,
                    onCheckedChange = { !isUsed }, // ponho dirieto quando se puser a base de dados
                )
            }
        }
    }
}
