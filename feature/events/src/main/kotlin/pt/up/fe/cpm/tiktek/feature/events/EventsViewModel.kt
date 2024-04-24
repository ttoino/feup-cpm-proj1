package pt.up.fe.cpm.tiktek.feature.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.model.Event
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EventsViewModel
    @Inject
    constructor(
        private val eventsRepository: EventsRepository,
    ) : ViewModel() {
        val events: StateFlow<List<Event>> =
            eventsRepository.getEvents().map {
                it.filter { event ->
                    Clock.System.now().toLocalDateTime(TimeZone.UTC).date <= event.date
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )

        val recommendedEvents: StateFlow<List<Event>> =
            events.map {
                it.sortedBy { event -> Random(event.date.toEpochDays()).nextInt() }.take(20)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )

        val upcomingEvents: StateFlow<List<Event>> =
            events.map {
                it.sortedBy { event -> event.date }.take(50)
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )
    }
