package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var uiState by mutableStateOf(LoginUiState())
            private set

        fun updateEmail(email: String) {
            uiState = uiState.copy(email = email)
        }

        fun updatePassword(password: String) {
            uiState = uiState.copy(password = password)
        }

        fun login() =
            viewModelScope.launch {
                uiState = uiState.copy(isLoading = true)

                val result = userRepository.login(uiState.email, uiState.password)

                if (!result) {
                    uiState = uiState.copy(errorMessage = "Login failed")
                }

                uiState = uiState.copy(isLoading = false)
            }
    }
