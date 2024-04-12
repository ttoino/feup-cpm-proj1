package pt.up.fe.cpm.tiktek.cafeteria

import androidx.compose.runtime.Composable
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

    @Composable
    override fun Screen() = PurchasedProductsScreen(purchase = purchase)
}
