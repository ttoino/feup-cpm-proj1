package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.fragment.app.FragmentActivity
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
import pt.up.fe.cpm.tiktek.core.model.CartWithModels
import pt.up.fe.cpm.tiktek.core.model.Voucher
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import pt.up.fe.cpm.tiktek.core.ui.BiometricPrompt
import javax.inject.Inject

@HiltViewModel
class CartViewModel
    @Inject
    constructor(
        private val cartRepository: CartRepository,
        private val cafeteriaRepository: CafeteriaRepository,
        private val vouchersRepository: VouchersRepository,
        private val promptManager: BiometricPrompt,
    ) : ViewModel() {
        val biometricResult = promptManager.promptResults

        val cart =
            combine(
                cartRepository.getCart(),
                cafeteriaRepository.getCafeteriaItems(),
                vouchersRepository.getVouchers(),
            ) { cart, cafeteriaItems, vouchers ->
                CartWithModels(
                    items =
                        cart.items.mapKeys { (id, _) ->
                            cafeteriaItems.first { it.id == id }
                        },
                    vouchers =
                        cart.vouchers.mapTo(mutableSetOf()) { id ->
                            when (val voucher = vouchers.first { it.id == id }) {
                                is Voucher.Free ->
                                    VoucherWithModels.Free(
                                        id = voucher.id,
                                        item = cafeteriaItems.first { it.id == voucher.itemId },
                                        userId = voucher.userId,
                                        orderId = voucher.orderId,
                                    )
                                is Voucher.Discount ->
                                    VoucherWithModels.Discount(
                                        id = voucher.id,
                                        discount = voucher.discount,
                                        userId = voucher.userId,
                                        orderId = voucher.orderId,
                                    )
                            }
                        },
                )
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CartWithModels(),
            )

        fun addItem(itemId: String) =
            viewModelScope.launch {
                cartRepository.addItem(itemId)
            }

        fun removeItem(itemId: String) =
            viewModelScope.launch {
                cartRepository.removeItem(itemId)
            }

        fun bioAuth(activity: FragmentActivity) {
            promptManager.showBiometricPrompt(
                title = "Sample Prompt",
                description = "DESCRIÇAO",
                activity,
            )
        }
    }
