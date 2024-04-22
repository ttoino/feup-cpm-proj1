package pt.up.fe.cpm.tiktek.feature.tickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.model.Ticket
import javax.inject.Inject

@HiltViewModel
class TicketsViewModel
    @Inject
    constructor(
        private val ticketsRepository: TicketsRepository,
    ) : ViewModel() {
        init {
            viewModelScope.launch { ticketsRepository.sync() }
        }

        val tickets: StateFlow<List<Ticket>> =
            ticketsRepository.getTickets().stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList(),
            )
    }
