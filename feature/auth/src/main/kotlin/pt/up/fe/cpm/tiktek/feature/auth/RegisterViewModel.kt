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
import pt.up.fe.cpm.tiktek.core.model.validation.birthdateValidator
import pt.up.fe.cpm.tiktek.core.model.validation.cvcCcValidator
import pt.up.fe.cpm.tiktek.core.model.validation.emailValidator
import pt.up.fe.cpm.tiktek.core.model.validation.expirationDateCcValidator
import pt.up.fe.cpm.tiktek.core.model.validation.nameValidator
import pt.up.fe.cpm.tiktek.core.model.validation.nifValidator
import pt.up.fe.cpm.tiktek.core.model.validation.numberCcValidator
import pt.up.fe.cpm.tiktek.core.model.validation.passwordValidator
import pt.up.fe.cpm.tiktek.core.model.validation.termsAcceptedValidator
import javax.inject.Inject

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val name = FormFieldUseCase("", nameValidator)
        val nif = FormFieldUseCase("", nifValidator) { it.filter { it.isDigit() }.take(9) }
        val birthdate = FormFieldUseCase(null, birthdateValidator)
        val email = FormFieldUseCase("", emailValidator)
        val password = FormFieldUseCase("", passwordValidator)
        val nameCc = FormFieldUseCase("", nameValidator)
        val numberCc = FormFieldUseCase("", numberCcValidator) { it.filter { it.isDigit() }.take(16) }
        val expirationDateCc = FormFieldUseCase("", expirationDateCcValidator) { it.filter { it.isDigit() }.take(4) }
        val cvcCc = FormFieldUseCase("", cvcCcValidator) { it.filter { it.isDigit() }.take(3) }
        val termsAccepted = FormFieldUseCase(false, termsAcceptedValidator)

        var uiState by mutableStateOf(RegisterUiState())
            private set

        val canContinue: Boolean
            get() =
                name.state.valid &&
                    nif.state.valid &&
                    birthdate.state.valid &&
                    email.state.valid &&
                    password.state.valid

        val canRegister: Boolean
            get() =
                canContinue &&
                    nameCc.state.valid &&
                    numberCc.state.valid &&
                    expirationDateCc.state.valid &&
                    cvcCc.state.valid &&
                    termsAccepted.state.valid

        fun register() =
            viewModelScope.launch {
                uiState = uiState.copy(isLoading = true)

                val result =
                    userRepository.register(
                        name = name.state.value,
                        nif = nif.state.value,
                        birthdate = birthdate.state.value!!,
                        email = email.state.value,
                        password = password.state.value,
                        nameCc = nameCc.state.value,
                        numberCc = numberCc.state.value,
                        expirationDateCc = expirationDateCc.state.value,
                        cvvCc = cvcCc.state.value,
                    )

                if (!result) {
                    uiState = uiState.copy(errorMessage = "Failed to register")
                }

                uiState = uiState.copy(isLoading = false)
            }
    }
