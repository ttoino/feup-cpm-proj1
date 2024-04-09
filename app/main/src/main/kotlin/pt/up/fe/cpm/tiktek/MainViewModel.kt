package pt.up.fe.cpm.tiktek

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val userRepository: UserRepository) : ViewModel() {
        val loggedIn: Flow<Boolean> = userRepository.getToken().map { it != null }
    }
