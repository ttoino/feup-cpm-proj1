package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.Voucher

interface VoucherDAO {
    suspend fun getAllByUser(userEmail: String): List<Voucher>

    suspend fun getById(id: String): Voucher?

    suspend fun create(voucher: Voucher): Voucher

    suspend fun update(voucher: Voucher): Voucher

    suspend fun delete(id: String): Boolean
}
