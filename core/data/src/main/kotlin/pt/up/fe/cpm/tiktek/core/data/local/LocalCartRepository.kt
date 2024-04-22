package pt.up.fe.cpm.tiktek.core.data.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.local.LocalCartDataSource
import pt.up.fe.cpm.tiktek.core.model.Cart
import javax.inject.Inject

class LocalCartRepository
    @Inject
    constructor(private val dataSource: LocalCartDataSource) : CartRepository {
        override fun getCart(): Flow<Cart> = dataSource.cart()

        override suspend fun resetCart() = dataSource.reset()

        override suspend fun addItem(
            item: String,
            quantity: Int,
        ) = dataSource.addItem(item, quantity)

        override suspend fun removeItem(item: String) = dataSource.removeItem(item)

        override suspend fun addVoucher(voucher: String) = dataSource.addVoucher(voucher)

        override suspend fun removeVoucher(voucher: String) = dataSource.removeVoucher(voucher)
    }
