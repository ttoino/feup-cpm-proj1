package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.OrdersRepository
import pt.up.fe.cpm.tiktek.core.data.VouchersRepository
import pt.up.fe.cpm.tiktek.core.model.OrderItemWithModels
import pt.up.fe.cpm.tiktek.core.model.OrderWithModels
import pt.up.fe.cpm.tiktek.core.model.Voucher
import pt.up.fe.cpm.tiktek.core.model.VoucherWithModels
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel
    @Inject
    constructor(
        private val ordersRepository: OrdersRepository,
        private val cafeteriaRepository: CafeteriaRepository,
        private val vouchersRepository: VouchersRepository,
    ) : ViewModel() {
        val orders =
            combine(
                ordersRepository.getOrders(),
                cafeteriaRepository.getCafeteriaItems(),
                vouchersRepository.getVouchers(),
            ) { orders, items, vouchers ->
                orders.map { order ->
                    OrderWithModels(
                        id = order.id,
                        userId = order.userId,
                        items =
                            order.items.map {
                                OrderItemWithModels(
                                    item = items.first { item -> item.id == it.itemId },
                                    quantity = it.quantity,
                                )
                            },
                        vouchers =
                            vouchers.filter { voucher -> voucher.orderId == order.id }.map { voucher ->
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
                                            items.first { item -> item.id == voucher.itemId },
                                            voucher.userId,
                                            voucher.orderId,
                                        )
                                }
                            }.sortedBy {
                                when (it) {
                                    is VoucherWithModels.Free -> 0
                                    is VoucherWithModels.Discount -> 1
                                }
                            },
                    )
                }
            }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
