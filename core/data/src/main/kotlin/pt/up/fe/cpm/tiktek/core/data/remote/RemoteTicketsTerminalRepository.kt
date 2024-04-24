package pt.up.fe.cpm.tiktek.core.data.remote

import pt.up.fe.cpm.tiktek.core.data.TicketsTerminalRepository
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.Ticket
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class RemoteTicketsTerminalRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
    ) : TicketsTerminalRepository {
        override suspend fun sendTicket(ticket: Ticket): NetworkResult<TicketWithEvent> {
            var result =
                networkDataSource.sendTicket(
                    ticket,
                )
            return result
        }
    }
