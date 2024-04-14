package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.domain.FormFieldUseCase
import pt.up.fe.cpm.tiktek.core.model.validation.emailValidator
import pt.up.fe.cpm.tiktek.core.model.validation.passwordValidator
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class LoginViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val email = FormFieldUseCase("", emailValidator)
        val password = FormFieldUseCase("", passwordValidator)

        var uiState by mutableStateOf(LoginUiState())
            private set

        val canLogin: Boolean
            get() = email.state.valid && password.state.valid

        fun login() =
            viewModelScope.launch {
                if (!canLogin) return@launch

                uiState = uiState.copy(isLoading = true)

                val result = userRepository.login(email.state.value, password.state.value)

                if (!result) {
                    uiState = uiState.copy(errorMessage = "Login failed")
                }

                uiState = uiState.copy(isLoading = false)
            }
    }
