package pt.up.fe.cpm.tiktek.feature.auth

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.domain.FormFieldUseCase
import pt.up.fe.cpm.tiktek.core.domain.NetworkErrorUseCase
import pt.up.fe.cpm.tiktek.core.domain.UnknownErrorUseCase
import pt.up.fe.cpm.tiktek.core.domain.ViolationUseCase
import pt.up.fe.cpm.tiktek.core.model.ErrorResponse
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.validation.emailValidator
import pt.up.fe.cpm.tiktek.core.model.validation.passwordValidator
import javax.inject.Inject

data class LoginUiState(
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
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

                uiState = uiState.copy(isLoading = true, errorMessage = null)

                when (val result = userRepository.login(email.state.value, password.state.value)) {
                    is NetworkResult.Success -> Unit
                    is NetworkResult.Failure -> uiState = uiState.copy(errorMessage = NetworkErrorUseCase())
                    is NetworkResult.Error ->
                        when (val error = result.error) {
                            is ErrorResponse.Unknown -> uiState = uiState.copy(errorMessage = UnknownErrorUseCase())
                            is ErrorResponse.GeneralViolation -> uiState = uiState.copy(errorMessage = ViolationUseCase(error.violation))
                            is ErrorResponse.FieldValidation ->
                                error.violations.forEach { (k, v) ->
                                    when (k) {
                                        "email" -> email.updateError(ViolationUseCase(v))
                                        "password" -> password.updateError(ViolationUseCase(v))
                                    }
                                }
                        }
                }

                uiState = uiState.copy(isLoading = false)
            }
    }
