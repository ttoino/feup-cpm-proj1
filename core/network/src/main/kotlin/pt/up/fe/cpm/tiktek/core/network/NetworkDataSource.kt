package pt.up.fe.cpm.tiktek.core.network

import pt.up.fe.cpm.tiktek.core.model.Event

interface NetworkDataSource {
    suspend fun getEvents(): List<Event>
}