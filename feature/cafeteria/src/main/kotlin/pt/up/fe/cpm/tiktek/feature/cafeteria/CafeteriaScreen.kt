package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.cafeteria.destinations.CartRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

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
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun CafeteriaScreen(
    cafeteriaItems: List<CafeteriaItem> = emptyList(),
    onCart: () -> Unit,
) {
    val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehaviour,
                title = { Text("Cafeteria") },
                actions = {
                    Row {
                        Spacer(modifier = Modifier.width(16.dp))
                        TextButton(
                            onClick = onCart,
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("Ver carrinho")
                        }
                    }
                },
            )
        },
    ) {
        Column(
            // horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier =
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
                    .padding(16.dp),
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
                    fontSize = 15.sp,
                    color = Color(0xffb2f98a),
                )
                Spacer(modifier = Modifier.width(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd,
                ) {
                    Text(
                        text = "Horário: 9h00 - 23h30",
                        color = Color(0xffb2f98a),
                    )
                }
            }
            Text(
                text = "Menu",
                style = TextStyle(fontSize = 20.sp),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                maxItemsInEachRow = 2,
            ) {
                cafeteriaItems.forEach { cafeteriaItem ->
                    ItemCafeteria(
                        itemName = cafeteriaItem.name,
                        itemLinkImg = cafeteriaItem.imageUrl,
                        itemPrice = cafeteriaItem.price / 100.0,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
internal fun ItemCafeteria(
    itemName: String,
    itemLinkImg: String,
    itemPrice: Double,
    modifier: Modifier,
) {
    Card(
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        modifier = modifier,
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(5f / 3f)
                    .height(0.dp),
        ) {
            AsyncImage(
                model = itemLinkImg,
                contentDescription =
                    "Cafeteria Image - an environment with tables " +
                        "and lights the window scenery shows the ending of an afternoon",
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = itemName,
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .padding(
                        start = 10.dp,
                        top = 10.dp,
                    ),
            fontSize = 20.sp,
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        top = 0.dp,
                        bottom = 8.dp,
                        end = 8.dp,
                    ),
        ) {
            Text(
                text = "$itemPrice €",
                textAlign = TextAlign.Center,
                modifier =
                    Modifier
                        .padding(
                            top = 10.dp,
                            bottom = 2.dp,
                            start = 2.dp,
                        ),
            )

            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier =
                    Modifier
                        .padding(
                            bottom = 2.dp,
                            end = 2.dp,
                        ),
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to cart")
            }
        }
    }
}
