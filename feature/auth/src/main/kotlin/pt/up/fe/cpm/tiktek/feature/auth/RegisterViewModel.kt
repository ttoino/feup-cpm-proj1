package pt.up.fe.cpm.tiktek.feature.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDate
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import javax.inject.Inject

data class RegisterUiState(
    val name: String = "",
    val nif: String = "",
    val birthdate: String = "",
    val email: String = "",
    val password: String = "",
    val nameCc: String = "",
    val numberCc: String = "",
    val expirationDateCc: String = "",
    val cvcCc: String = "",
    val termsAccepted: Boolean = false,
    val secondStage: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class RegisterViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        var uiState by mutableStateOf(RegisterUiState())
            private set

        fun updateName(name: String) {
            uiState = uiState.copy(name = name)
        }

        fun updateNif(nif: String) {
            uiState = uiState.copy(nif = nif.filter { it.isDigit() }.take(9))
        }

        fun updateBirthdate(birthdate: String) {
            uiState = uiState.copy(birthdate = birthdate)
        }

        fun updateEmail(email: String) {
            uiState = uiState.copy(email = email)
        }

        fun updatePassword(password: String) {
            uiState = uiState.copy(password = password)
        }

        fun updateNameCc(nameCc: String) {
            uiState = uiState.copy(nameCc = nameCc)
        }

        fun updateNumberCc(numberCc: String) {
            uiState = uiState.copy(numberCc = numberCc.filter { it.isDigit() }.take(16))
        }

        fun updateExpirationDateCc(expirationDateCc: String) {
            uiState = uiState.copy(expirationDateCc = expirationDateCc.filter { it.isDigit() }.take(4))
        }

        fun updateCvcCc(cvvCc: String) {
            uiState = uiState.copy(cvcCc = cvvCc.filter { it.isDigit() }.take(3))
        }

        fun updateTermsAccepted(termsAccepted: Boolean) {
            uiState = uiState.copy(termsAccepted = termsAccepted)
        }

        fun register() =
            viewModelScope.launch {
                uiState = uiState.copy(isLoading = true)

                val result =
                    userRepository.register(
                        name = uiState.name,
                        nif = uiState.nif,
                        birthdate = uiState.birthdate.toLocalDate(),
                        email = uiState.email,
                        password = uiState.password,
                        nameCc = uiState.nameCc,
                        numberCc = uiState.numberCc,
                        expirationDateCc = uiState.expirationDateCc.take(2) + "/" + uiState.expirationDateCc.takeLast(2),
                        cvvCc = uiState.cvcCc,
                    )

                if (!result) {
                    uiState = uiState.copy(errorMessage = "Failed to register")
                }

                uiState = uiState.copy(isLoading = false)
            }
    }
