package pt.up.fe.cpm.tiktek.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import javax.inject.Inject

@HiltViewModel
class EventsViewModel
    @Inject
    constructor(
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch { eventsRepository.sync() }
        }
    }
