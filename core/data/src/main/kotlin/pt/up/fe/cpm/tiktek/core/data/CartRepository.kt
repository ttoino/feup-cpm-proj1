package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Cart

interface CartRepository {
    fun getCart(): Flow<Cart>

    suspend fun resetCart()

    suspend fun addItem(
        item: String,
        quantity: Int = 1,
    )

    suspend fun removeItem(item: String)

    suspend fun addVoucher(voucher: String)

    suspend fun removeVoucher(voucher: String)
}
