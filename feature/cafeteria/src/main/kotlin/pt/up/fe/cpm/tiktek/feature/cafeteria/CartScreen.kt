package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import com.ramcosta.composedestinations.generated.cafeteria.destinations.VouchersDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

@Destination<CafeteriaGraph>(
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CartRoute(navigator: DestinationsNavigator) {
    CartScreen(navigator)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CartScreen(navigator: DestinationsNavigator) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text("Carrinho") },
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
            // colocar aqui todos os items

            ItemCard(
                "Sanduíche",
                "https://i.pinimg.com/564x/ae/ee/c1/aeeec154c1058118a57e6b83d08bdd32.jpg",
                2.55,
                3,
            )
            ItemCard(
                "Sanduíche",
                "https://i.pinimg.com/564x/ae/ee/c1/aeeec154c1058118a57e6b83d08bdd32.jpg",
                2.55,
                3,
            )
            ItemCard(
                "Sanduíche",
                "https://i.pinimg.com/564x/ae/ee/c1/aeeec154c1058118a57e6b83d08bdd32.jpg",
                2.55,
                3,
            )
            ItemCard(
                "Sanduíche",
                "https://i.pinimg.com/564x/ae/ee/c1/aeeec154c1058118a57e6b83d08bdd32.jpg",
                2.55,
                3,
            )

            // ------------- fim dos items --------------------

            HorizontalDivider(thickness = 5.dp, color = MaterialTheme.colorScheme.primaryContainer)
            // Botão Adicionar Voucher
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth(),
            ) {
                Button(
                    onClick = { navigator.navigate(VouchersDestination()) },
                    modifier =
                        Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 8.dp)
                            .padding(bottom = 40.dp)
                            .fillMaxWidth(),
                ) {
                    Text(text = "Adicionar voucher")
                }
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
                    onClick = { navigator.navigateUp() },
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
                    onClick = {
                        navigator.navigate(
                            com.ramcosta.composedestinations.generated.cafeteria.destinations.CafeteriaBuyDialogDestination(orderId = ""),
                        )
                    },
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
}

@Composable
internal fun ItemCard(
    itemName: String,
    itemLinkImg: String,
    itemPrice: Double,
    itemQuantity: Int,
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
            Column(
                modifier =
                    Modifier.padding(16.dp)
                        .fillMaxHeight(),
            ) {
                Text(
                    text = itemName,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Spacer(modifier = Modifier.weight(1f)) // Spacer to push text to bottom
                Text(
                    text = "$itemPrice € / un",
                    fontSize = 15.sp,
                )
            }
            Column {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 15.dp),
                ) {
                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Minus",
                        )
                    }
                    InputChip(
                        onClick = { },
                        label = { Text("$itemQuantity") },
                        selected = true,
                    )

                    IconButton(
                        onClick = { },
                        modifier = Modifier.size(48.dp),
                        colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Plus",
                        )
                    }
                }
            }
            AsyncImage(
                model = itemLinkImg,
                contentDescription = "Food Image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(80.dp),
            )
        }
    }
}

@Destination<CafeteriaGraph>(style = DestinationStyle.Dialog::class)
@Composable
fun CafeteriaBuyDialog(
    orderId: String,
    navigator: DestinationsNavigator,
) {
    CafeteriaBuyDialogContent(
        orderId,
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
    orderId: String,
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
