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
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.parameters.CodeGenVisibility
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import pt.up.fe.cpm.tiktek.core.ui.relativeOffset
import pt.up.fe.cpm.tiktek.feature.cafeteria.navigation.CafeteriaGraph

@Destination<CafeteriaGraph>(
    start = true,
    visibility = CodeGenVisibility.INTERNAL,
)
@Composable
internal fun CafeteriaRoute() {
    // TODO: Get data

    CafeteriaScreen()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
internal fun CafeteriaScreen() {
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
                        TextButton(onClick = {/*mandar p outra pag */ }) {
                            Text("Ver carrinho")
                        }
                    }
                }
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
                .padding(16.dp)
        )
        {

            AsyncImage(
                model = "https://i.pinimg.com/564x/b8/85/4c/b8854cfb077f5e7f6646899455f27704.jpg",
                contentDescription = "Cafeteria Image - an environment with tables and lights the window scenery shows the ending of an afternoon",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16.dp / 9.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Menu",
                style = TextStyle(fontSize = 20.sp),
            )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                maxItemsInEachRow = 2
            ) {
                ItemCafeteria(
                    itemName = "Café",
                    itemLinkImg = "https://i.pinimg.com/564x/c9/c3/3a/c9c33a1344689e3dff43e51dddb572ce.jpg",
                    itemPrice = 0.67,
                    modifier = Modifier.weight(1f)
                )
                ItemCafeteria(
                    itemName = "Pipoca",
                    itemLinkImg = "https://i.pinimg.com/564x/b8/98/f5/b898f57f93e6ef44d43940c22c92564c.jpg",
                    itemPrice = 1.23,
                    modifier = Modifier.weight(1f)
                )
                ItemCafeteria(
                    itemName = "Sanduíche",
                    itemLinkImg = "https://i.pinimg.com/564x/ae/ee/c1/aeeec154c1058118a57e6b83d08bdd32.jpg",
                    itemPrice = 2.55,
                    modifier = Modifier.weight(1f)
                )
                ItemCafeteria(
                    itemName = "Refrigerante",
                    itemLinkImg = "https://i.pinimg.com/564x/0d/99/d9/0d99d9474c57590b2f0814fbcbc3f138.jpg",
                    itemPrice = 0.90,
                    modifier = Modifier.weight(1f)
                )
            }


        }
    }

}

@Composable
internal fun ItemCafeteria(
    itemName: String,
    itemLinkImg: String,
    itemPrice: Double,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 3f)
                .height(0.dp)
        ) {
            AsyncImage(
                model = itemLinkImg,
                contentDescription = "Cafeteria Image - an environment with tables and lights the window scenery shows the ending of an afternoon",
                contentScale = ContentScale.Crop
            )
        }
        Text(
            text = itemName,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 10.dp
                    ),
            fontSize = 20.sp
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 8.dp,
                    top = 0.dp,
                    bottom = 8.dp,
                    end = 8.dp
                )

        ) {
            Text(
                text = "$itemPrice €",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        bottom = 2.dp,
                        start = 2.dp
                    )
            )

            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(
                        bottom = 2.dp,
                        end = 2.dp
                    )
            ) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Add to cart")
            }
        }
    }


}
