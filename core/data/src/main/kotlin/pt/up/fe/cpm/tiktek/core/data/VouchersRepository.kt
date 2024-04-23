package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.Voucher

interface VouchersRepository {
    fun getVouchers(): Flow<List<Voucher>>
}
