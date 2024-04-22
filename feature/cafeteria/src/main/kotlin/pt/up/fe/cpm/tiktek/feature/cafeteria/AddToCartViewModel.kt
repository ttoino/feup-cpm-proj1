package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.cafeteria.destinations.AddToCartDialogDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import javax.inject.Inject

data class AddToCartUiState(
    val quantity: Int = 1,
    val loading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class AddToCartViewModel
    @Inject
    constructor(
        private val cafeteriaRepository: CafeteriaRepository,
        private val cartRepository: CartRepository,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val navArgs = AddToCartDialogDestination.argsFrom(savedStateHandle)

        val itemId = navArgs.itemId

        val item =
            cafeteriaRepository.getCafeteriaItem(itemId).stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null,
            )

        var uiState by mutableStateOf(AddToCartUiState())
            private set

        fun remove() {
            uiState = uiState.copy(quantity = maxOf(uiState.quantity - 1, 1))
        }

        fun add() {
            uiState = uiState.copy(quantity = uiState.quantity + 1)
        }

        fun addToCart() =
            viewModelScope.launch {
                cartRepository.addItem(itemId, uiState.quantity)
            }
    }
