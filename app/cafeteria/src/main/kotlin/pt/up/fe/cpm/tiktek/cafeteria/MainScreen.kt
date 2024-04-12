package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddShoppingCart
import androidx.compose.material.icons.outlined.ConfirmationNumber
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun MainScreen() {
    val sdf = SimpleDateFormat("HH:mm")
    var currentDateAndTime by remember { mutableStateOf(sdf.format(Date())) }

    LaunchedEffect(Unit) {
        while (true) {
            currentDateAndTime = sdf.format(Date())
            delay(1000) // Update every second
        }
    }

    Scaffold {
        Column(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(it)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = currentDateAndTime,
                fontSize = 50.sp,
            )
            Spacer(
                modifier = Modifier.height(50.dp),
            )
            Text(
                text = "Terminal Cafetaria",
                fontSize = 35.sp,
            )
            Spacer(
                modifier = Modifier.height(30.dp),
            )
            Text(
                text = "Clique no botão abaixo para validar o seu carrinho",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
            Spacer(
                modifier = Modifier.height(30.dp),
            )
            Text(
                text = "Pode encontrar o QR-code do seu carrinho na página do carrinho quando clicar no botão “Efetuar Compra” na aplicação TikTek",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.outline,
                )
            Spacer(
                modifier = Modifier.height(150.dp),
            )
            Button(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Outlined.AddShoppingCart,
                    contentDescription = "Validar carrinho de compras",
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = "Validar Carrinho")
            }
        }
    }
}
