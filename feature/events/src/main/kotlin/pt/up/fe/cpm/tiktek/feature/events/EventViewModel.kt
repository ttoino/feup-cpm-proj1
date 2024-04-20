package pt.up.fe.cpm.tiktek.feature.events

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.generated.events.destinations.EventDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject

@HiltViewModel
class EventViewModel
    @Inject
    constructor(
        private val savedStateHandle: SavedStateHandle,
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        private val navArgs = EventDestination.argsFrom(savedStateHandle)

        val eventId = navArgs.eventId

        val event: StateFlow<Event?> =
            eventsRepository.getEvent(
                eventId,
            ).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }
