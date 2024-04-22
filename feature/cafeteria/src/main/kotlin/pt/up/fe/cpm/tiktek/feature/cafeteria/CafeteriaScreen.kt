package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.cafeteria.destinations.AddToCartDialogDestination
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.core.ui.AppBarLayout
import pt.up.fe.cpm.tiktek.core.ui.theme.TikTekTheme
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph
import pt.up.fe.cpm.tiktek.feature.cafeteria.ui.CafeteriaItemCard

@Destination<CafeteriaGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CafeteriaRoute(
    navigator: DestinationsNavigator,
    viewModel: CafeteriaViewModel = hiltViewModel(),
) {
    val cafeteriaItems by viewModel.cafeteriaItems.collectAsState(emptyList())

    CafeteriaScreen(
        cafeteriaItems = cafeteriaItems,
        onCart = { navigator.navigate(CartRouteDestination) },
        onAddToCart = { navigator.navigate(AddToCartDialogDestination(it)) },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CafeteriaScreen(
    cafeteriaItems: List<CafeteriaItem> = emptyList(),
    onCart: () -> Unit,
    onAddToCart: (String) -> Unit,
) {
    AppBarLayout(
        title = "Cafeteria",
        actions = {
            TextButton(
                onClick = onCart,
                modifier = Modifier.padding(8.dp),
            ) {
                Text("Ver carrinho")
            }
        },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp),
    ) {
        AsyncImage(
            model = "https://i.pinimg.com/564x/b8/85/4c/b8854cfb077f5e7f6646899455f27704.jpg",
            contentDescription =
                "Cafeteria Image - an environment with tables " +
                    "and lights the window scenery shows the ending of an afternoon",
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(16.dp / 9.dp),
            contentScale = ContentScale.Fit,
        )

        // __________________________________Time open ________________________________________

        Row {
            Text(
                text = "Aberto",
                color = TikTekTheme.extendedColorScheme.success,
            )
            Spacer(
                modifier = Modifier.defaultMinSize(minWidth = 16.dp).weight(1f),
            )
            Text(
                text = "Horário: 9h00 - 23h30",
                color = TikTekTheme.extendedColorScheme.success,
                textAlign = TextAlign.End,
            )
        }

        Text(
            text = "Menu",
            style = MaterialTheme.typography.headlineSmall,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            maxItemsInEachRow = 2,
        ) {
            cafeteriaItems.forEach { cafeteriaItem ->
                CafeteriaItemCard(
                    item = cafeteriaItem,
                    onAddToCart = { onAddToCart(cafeteriaItem.id) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}
