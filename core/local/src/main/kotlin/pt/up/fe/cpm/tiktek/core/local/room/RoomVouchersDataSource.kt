package pt.up.fe.cpm.tiktek.core.local.room

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.local.LocalVouchersDataSource
import pt.up.fe.cpm.tiktek.core.model.Voucher
import javax.inject.Inject

class RoomVouchersDataSource
    @Inject
    constructor(
        private val database: Database,
    ) : LocalVouchersDataSource {
        override fun getVouchers(): Flow<List<Voucher>> = database.voucher.getAll().map { it.map { it.toVoucher() } }

        override fun getVoucher(id: String): Flow<Voucher> = database.voucher.getById(id).map { it.toVoucher() }

        override suspend fun insert(vouchers: List<Voucher>) = database.voucher.insertAll(vouchers.map { it.toEntity() })

        override suspend fun deleteVouchers() = database.voucher.deleteAll()
    }

@Dao
internal interface VoucherDao {
    @Query("SELECT * FROM vouchers")
    fun getAll(): Flow<List<VoucherEntity>>

    @Query("SELECT * FROM vouchers WHERE id = :id")
    fun getById(id: String): Flow<VoucherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vouchers: List<VoucherEntity>)

    @Query("DELETE FROM vouchers")
    suspend fun deleteAll()
}

@Entity(tableName = "vouchers")
internal data class VoucherEntity(
    @PrimaryKey
    val id: String,
    val discount: Int?,
    val itemId: String?,
    val userEmail: String,
    val orderId: String?,
)

internal fun Voucher.toEntity() =
    VoucherEntity(
        id = id,
        discount = (this as? Voucher.Discount)?.discount,
        itemId = (this as? Voucher.Free)?.itemId,
        userEmail = userEmail,
        orderId = orderId,
    )

internal fun VoucherEntity.toVoucher() =
    discount?.let {
        Voucher.Discount(
            id = id,
            discount = it,
            userEmail = userEmail,
            orderId = orderId,
        )
    } ?: itemId?.let {
        Voucher.Free(
            id = id,
            itemId = it,
            userEmail = userEmail,
            orderId = orderId,
        )
    } ?: throw IllegalArgumentException("Invalid voucher entity")
