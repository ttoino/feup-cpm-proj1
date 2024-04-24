package pt.up.fe.cpm.tiktek.core.data.remote

import pt.up.fe.cpm.tiktek.core.data.TicketsTerminalRepository
import pt.up.fe.cpm.tiktek.core.model.NetworkResult
import pt.up.fe.cpm.tiktek.core.model.SendTicketRequest
import pt.up.fe.cpm.tiktek.core.model.TicketWithEvent
import pt.up.fe.cpm.tiktek.core.network.NetworkDataSource
import javax.inject.Inject

class RemoteTicketsTerminalRepository
    @Inject
    constructor(
        private val networkDataSource: NetworkDataSource,
    ) : TicketsTerminalRepository {
        override suspend fun sendTicket(request: SendTicketRequest): NetworkResult<TicketWithEvent> = networkDataSource.sendTicket(request)
    }
