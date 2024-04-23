package pt.up.fe.cpm.tiktek.feature.profile

import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.domain.FormFieldUseCase
import pt.up.fe.cpm.tiktek.core.domain.NetworkErrorUseCase
import pt.up.fe.cpm.tiktek.core.domain.UnknownErrorUseCase
import pt.up.fe.cpm.tiktek.core.domain.ViolationUseCase
import pt.up.fe.cpm.tiktek.core.model.ErrorResponse
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.validation.birthdateValidator
import pt.up.fe.cpm.tiktek.core.model.validation.cvcCcValidator
import pt.up.fe.cpm.tiktek.core.model.validation.emailValidator
import pt.up.fe.cpm.tiktek.core.model.validation.expirationDateCcValidator
import pt.up.fe.cpm.tiktek.core.model.validation.nameValidator
import pt.up.fe.cpm.tiktek.core.model.validation.nifValidator
import pt.up.fe.cpm.tiktek.core.model.validation.numberCcValidator
import javax.inject.Inject

data class ProfileUiState(
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int? = null,
)

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        val user =
            userRepository.getUser().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null,
            )

        init {
            viewModelScope.launch {
                user.collect {
                    if (it == null) return@collect

                    name.update(it.name)
                    nif.update(it.nif)
                    birthdate.update(it.birthdate)
                    email.update(it.email)

                    nameCc.update(it.nameCc)
                    numberCc.update(it.numberCc)
                    expirationDateCc.update(it.expirationDateCc)
                    cvcCc.update(it.cvvCc)
                }
            }
        }

        var uiState by mutableStateOf(ProfileUiState())
            private set

        val successPersonal = MutableStateFlow(false)
        val successCreditCard = MutableStateFlow(false)

        // Personal information
        val name = FormFieldUseCase("", nameValidator)
        val nif = FormFieldUseCase("", nifValidator) { it.filter { it.isDigit() }.take(9) }
        val birthdate = FormFieldUseCase(null, birthdateValidator)
        val email = FormFieldUseCase("", emailValidator)

        val canUpdatePersonalInformation
            get() =
                name.state.valid &&
                    nif.state.valid &&
                    birthdate.state.valid &&
                    email.state.valid

        fun updatePersonalInformation() =
            viewModelScope.launch {
                if (!canUpdatePersonalInformation) return@launch

                uiState = uiState.copy(isLoading = true, errorMessage = null)

                val result =
                    userRepository.updatePersonalInformation(
                        name.state.value,
                        nif.state.value,
                        birthdate.state.value!!,
                        email.state.value,
                    )

                when (result) {
                    is NetworkResult.Success -> successPersonal.value = true
                    is NetworkResult.Failure -> {
                        uiState =
                            uiState.copy(errorMessage = NetworkErrorUseCase())
                    }
                    is NetworkResult.Error -> {
                        when (val error = result.error) {
                            is ErrorResponse.Unknown ->
                                uiState =
                                    uiState.copy(errorMessage = UnknownErrorUseCase())

                            is ErrorResponse.GeneralViolation ->
                                uiState =
                                    uiState.copy(errorMessage = ViolationUseCase(error.violation))

                            is ErrorResponse.FieldValidation -> {
                                error.violations.forEach { (k, v) ->
                                    when (k) {
                                        "name" -> name.updateError(ViolationUseCase(v))
                                        "nif" -> nif.updateError(ViolationUseCase(v))
                                        "birthdate" -> birthdate.updateError(ViolationUseCase(v))
                                        "email" -> email.updateError(ViolationUseCase(v))
                                    }
                                }
                            }
                        }
                    }
                }
                uiState = uiState.copy(isLoading = false)
            }

        // Payment information

        val nameCc = FormFieldUseCase("", nameValidator)
        val numberCc = FormFieldUseCase("", numberCcValidator) { it.filter { it.isDigit() }.take(16) }
        val expirationDateCc = FormFieldUseCase("", expirationDateCcValidator) { it.filter { it.isDigit() }.take(4) }
        val cvcCc = FormFieldUseCase("", cvcCcValidator) { it.filter { it.isDigit() }.take(3) }

        val canUpdatePaymentInformation get() =
            nameCc.state.valid &&
                numberCc.state.valid &&
                expirationDateCc.state.valid &&
                cvcCc.state.valid

        fun updatePaymentInformation() =
            viewModelScope.launch {
                if (!canUpdatePaymentInformation) return@launch

                uiState = uiState.copy(isLoading = true, errorMessage = null)

                val result =
                    userRepository.updatePaymentInformation(
                        nameCc.state.value,
                        numberCc.state.value,
                        expirationDateCc.state.value,
                        cvcCc.state.value,
                    )

                when (result) {
                    is NetworkResult.Success -> successCreditCard.value = true
                    is NetworkResult.Failure -> {
                        uiState =
                            uiState.copy(errorMessage = NetworkErrorUseCase())
                    }

                    is NetworkResult.Error -> {
                        when (val error = result.error) {
                            is ErrorResponse.Unknown ->
                                uiState =
                                    uiState.copy(errorMessage = UnknownErrorUseCase())

                            is ErrorResponse.GeneralViolation ->
                                uiState =
                                    uiState.copy(errorMessage = ViolationUseCase(error.violation))

                            is ErrorResponse.FieldValidation -> {
                                error.violations.forEach { (k, v) ->
                                    when (k) {
                                        "nameCc" -> nameCc.updateError(ViolationUseCase(v))
                                        "numberCc" -> numberCc.updateError(ViolationUseCase(v))
                                        "expirationDateCc" -> expirationDateCc.updateError(ViolationUseCase(v))
                                        "cvcCc" -> cvcCc.updateError(ViolationUseCase(v))
                                    }
                                }
                            }
                        }
                    }
                }
                uiState = uiState.copy(isLoading = false)
            }

        // Logout
        fun logout() =
            viewModelScope.launch {
                userRepository.logout()
            }
    }
