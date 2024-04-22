package pt.up.fe.cpm.tiktek.core.local.datastore

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import pt.up.fe.cpm.tiktek.core.local.LocalCartDataSource
import pt.up.fe.cpm.tiktek.core.model.Cart
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by dataStore("cart", serializer(Cart()))

@Singleton
class DataStoreCartDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : LocalCartDataSource {
        override fun cart() = context.dataStore.data

        override suspend fun reset() {
            context.dataStore.updateData {
                Cart()
            }
        }

        override suspend fun addItem(
            item: String,
            quantity: Int,
        ) {
            context.dataStore.updateData {
                it.copy(items = it.items + (item to it.items.getOrDefault(item, 0) + quantity))
            }
        }

        override suspend fun removeItem(item: String) {
            context.dataStore.updateData {
                it.copy(items = (it.items + (item to it.items.getOrDefault(item, 0) - 1)).filterValues { it > 0 })
            }
        }

        override suspend fun addVoucher(voucher: String) {
            context.dataStore.updateData {
                it.copy(vouchers = it.vouchers + voucher)
            }
        }

        override suspend fun removeVoucher(voucher: String) {
            context.dataStore.updateData {
                it.copy(vouchers = it.vouchers - voucher)
            }
        }
    }
