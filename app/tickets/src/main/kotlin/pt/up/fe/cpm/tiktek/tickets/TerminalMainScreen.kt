package pt.up.fe.cpm.tiktek.tickets
import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.TicketValidatedRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date

@Destination<RootGraph>(start = true)
@Composable
internal fun TerminalMainScreenRoute(navigator: DestinationsNavigator) {
    TerminalMainScreen(navigator)
}

@Composable
fun TerminalMainScreen(navigator: DestinationsNavigator) {
    val sdf = SimpleDateFormat("HH:mm")
    var currentDateAndTime by remember { mutableStateOf(sdf.format(Date())) }
    val context = LocalContext.current
    var scannedQRCodeResult by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        while (true) {
            currentDateAndTime = sdf.format(Date())
            delay(1000) // Update every second
        }
    }

    val barCodeLauncher =
        rememberLauncherForActivityResult(ScanContract()) {
                result ->
            if (result.contents == null) {
                Toast.makeText(context, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                scannedQRCodeResult = result.contents
                Log.d("MainActivity", "Scanned result: $result")

                // redirect to screen
                navigator.navigate(TicketValidatedRouteDestination(scannedQRCodeResult))
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
                text = "Terminal Bilheteria Eventos",
                fontSize = 27.sp,
            )
            Spacer(
                modifier = Modifier.height(30.dp),
            )
            Text(
                text = "Clique no botão abaixo para validar o seu bilhete",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
            )
            Spacer(
                modifier = Modifier.height(30.dp),
            )
            Text(
                text = "Pode encontrar o QR-code do seu bilhete clicando no seu bilhete na aba “Meus Bilhetes” da aplicação TikTek",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.outline,
            )
            Spacer(
                modifier = Modifier.height(150.dp),
            )
            Button(
                onClick = {
                    showCamera(barCodeLauncher)
                    Log.d("MainScreen", "Button clicked")
                },
            ) {
                Icon(
                    imageVector = Icons.Outlined.ConfirmationNumber,
                    contentDescription = "Validar bilhete",
                    modifier = Modifier.size(18.dp),
                )
                Spacer(
                    modifier = Modifier.width(8.dp),
                )
                Text(text = "Validar Bilhete")
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun getCameraPermission(content: @Composable () -> Unit) {
    val cameraPermissionState =
        rememberPermissionState(
            Manifest.permission.CAMERA,
        )
    if (cameraPermissionState.status.isGranted) {
        content()
    } else {
        Column {
            val textToShow =
                if (cameraPermissionState.status.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "The camera is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
                }
            Text(textToShow)
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("Request permission")
            }
        }
    }
}

fun showCamera(barCodeLauncher: ActivityResultLauncher<ScanOptions>) {
    val options = ScanOptions()
    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
    options.setPrompt("Scan your cart QR code")
    options.setCameraId(0)
    options.setBeepEnabled(false)
    options.setOrientationLocked(false)

    barCodeLauncher.launch(options)
}
