package pt.up.fe.cpm.tiktek

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val userRepository: UserRepository) : ViewModel() {
        val loggedIn = userRepository.getToken().map { it != null }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }
