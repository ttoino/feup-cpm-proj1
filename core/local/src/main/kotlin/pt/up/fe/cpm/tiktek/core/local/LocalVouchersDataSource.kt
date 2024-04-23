package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Voucher

interface LocalVouchersDataSource {
    fun getVouchers(): Flow<List<Voucher>>

    fun getVoucher(id: String): Flow<Voucher>

    suspend fun insert(vouchers: List<Voucher>)

    suspend fun deleteVouchers()
}
