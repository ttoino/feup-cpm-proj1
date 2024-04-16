package pt.up.fe.cpm.tiktek.feature.cafeteria

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem
import javax.inject.Inject

@HiltViewModel
class CafeteriaViewModel
    @Inject
    constructor(
        private val cafeteriaRepository: CafeteriaRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch {
                cafeteriaRepository.sync()
            }
        }

        val cafeteriaItems: Flow<List<CafeteriaItem>> = cafeteriaRepository.getCafeteriaItems()
    }
