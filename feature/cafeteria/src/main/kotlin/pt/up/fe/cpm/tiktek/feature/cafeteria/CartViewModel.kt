package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.model.CartWithModels
import javax.inject.Inject

@HiltViewModel
class CartViewModel
    @Inject
    constructor(
        private val cartRepository: CartRepository,
        private val cafeteriaRepository: CafeteriaRepository,
    ) : ViewModel() {
        val biometricResult = promptManager.promptResults

        val cart =
            combine(
                cartRepository.getCart(),
                cafeteriaRepository.getCafeteriaItems(),
            ) { cart, cafeteriaItems ->
                CartWithModels(
                    items =
                        cart.items.mapKeys { (id, _) ->
                            cafeteriaItems.first { it.id == id }
                        },
                    // TODO
                    vouchers = emptySet(),
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

        fun bioAuth()  {
            promptManager.showBiometricPrompt(
                title = "Sample Prompt",
                description = "DESCRIÇAO",
            )
        }
    }
