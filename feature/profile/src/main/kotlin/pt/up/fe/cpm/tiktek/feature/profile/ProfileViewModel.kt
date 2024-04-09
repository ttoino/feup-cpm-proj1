package pt.up.fe.cpm.tiktek.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        fun logout() =
            viewModelScope.launch {
                userRepository.logout()
            }
    }
