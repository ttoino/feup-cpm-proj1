package pt.up.fe.cpm.tiktek.feature.profile

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
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch {
                userRepository.getUser().collect {
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

        // Personal information
        val name = FormFieldUseCase("", nameValidator)
        val nif = FormFieldUseCase("", nifValidator) { it.filter { it.isDigit() }.take(9) }
        val birthdate = FormFieldUseCase(null, birthdateValidator)
        val email = FormFieldUseCase("", emailValidator)

        val canSavePersonalInformation get() =
            name.state.valid &&
                nif.state.valid &&
                birthdate.state.valid &&
                email.state.valid

        fun updatePersonalInformation() =
            viewModelScope.launch {
                // TODO
            }

        // Password
        val oldPassword = FormFieldUseCase("", passwordValidator)
        val newPassword = FormFieldUseCase("", passwordValidator)

        val canUpdatePassword get() =
            oldPassword.state.valid &&
                newPassword.state.valid

        fun updatePassword() =
            viewModelScope.launch {
                // TODO
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
                // TODO
            }

        // Logout
        fun logout() =
            viewModelScope.launch {
                userRepository.logout()
            }
    }
