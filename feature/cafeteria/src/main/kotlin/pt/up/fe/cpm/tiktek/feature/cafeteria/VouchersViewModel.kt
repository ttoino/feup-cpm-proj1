package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.data.VouchersRepository
import pt.up.fe.cpm.tiktek.core.model.Voucher
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import javax.inject.Inject

@HiltViewModel
class VouchersViewModel
    @Inject
    constructor(
        private val cartRepository: CartRepository,
        private val vouchersRepository: VouchersRepository,
        private val cafeteriaRepository: CafeteriaRepository,
    ) : ViewModel() {
        val vouchers =
            combine(
                vouchersRepository.getVouchers(),
                cartRepository.getCart(),
                cafeteriaRepository.getCafeteriaItems(),
            ) { vouchers, cart, cafeteriaItems ->
                vouchers.filter { it.orderId == null }.map { voucher ->
                    when (voucher) {
                        is Voucher.Discount ->
                            VoucherWithModels.Discount(
                                voucher.id,
                                voucher.discount,
                                voucher.userId,
                                voucher.orderId,
                            )
                        is Voucher.Free ->
                            VoucherWithModels.Free(
                                voucher.id,
                                cafeteriaItems.first { item -> item.id == voucher.itemId },
                                voucher.userId,
                                voucher.orderId,
                            )
                    } to cart.vouchers.contains(voucher.id)
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )

        fun addVoucher(voucherId: String) =
            viewModelScope.launch {
                cartRepository.addVoucher(voucherId)
            }

        fun removeVoucher(voucherId: String) =
            viewModelScope.launch {
                cartRepository.removeVoucher(voucherId)
            }
    }
