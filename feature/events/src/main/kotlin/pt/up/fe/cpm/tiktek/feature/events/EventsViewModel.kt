package pt.up.fe.cpm.tiktek.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject

@HiltViewModel
class EventsViewModel
    @Inject
    constructor(
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        val events: StateFlow<List<Event>> =
            eventsRepository.getEvents().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }
