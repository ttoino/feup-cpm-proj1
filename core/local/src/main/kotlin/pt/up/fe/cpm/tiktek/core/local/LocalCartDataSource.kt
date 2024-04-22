package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Cart

interface LocalCartDataSource {
    fun cart(): Flow<Cart>

    suspend fun reset()

    suspend fun addItem(
        item: String,
        quantity: Int = 1,
    )

    suspend fun removeItem(item: String)

    suspend fun addVoucher(voucher: String)

    suspend fun removeVoucher(voucher: String)
}
