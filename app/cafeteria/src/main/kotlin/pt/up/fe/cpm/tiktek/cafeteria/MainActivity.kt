package pt.up.fe.cpm.tiktek.cafeteria

import android.app.Instrumentation.ActivityResult
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import pt.up.fe.cpm.tiktek.core.app.ScreenActivity

@AndroidEntryPoint
class MainActivity : ScreenActivity() {
    val jsonString = """
    {
      "orderNumber": 123456,
      "orderProducts": [
        {
          "cafetariaItem": "Coffee",
          "quantity": 2,
          "totalPrice": 200
        },
        {
          "cafetariaItem": "Croissant",
          "quantity": 1,
          "totalPrice": 50
        }
      ],
      "orderVouchers": [
        {
          "voucherName": "1 café grátis",
          "voucherQuantity": 1
        }
      ]
    }
    """
    val purchase = JSONObject(jsonString)

    var textResult = mutableStateOf("")

    val barCodeLauncher = registerForActivityResult(ScanContract()){
            result ->
        if (result.contents == null){
            Toast.makeText(this@MainActivity, "Cancelled", Toast.LENGTH_SHORT).show()
        } else {
            textResult.value = result.contents
        }
    }

    fun showCamera() {
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan your cart QR code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setOrientationLocked(false)

        barCodeLauncher.launch((options))

    }
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        isGranted ->
        if(isGranted){
            showCamera()
        }
    }

    private fun checkCameraPermission(context: Context){
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)) {
            Toast.makeText(this@MainActivity, "Camera required", Toast.LENGTH_SHORT).show()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    @Composable
    override fun Screen() = MainScreen(::checkCameraPermission)
    //override fun Screen() = PurchasedProductsScreen(purchase = purchase)
}
