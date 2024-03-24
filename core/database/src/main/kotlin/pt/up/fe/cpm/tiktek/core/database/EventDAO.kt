package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.Event

interface EventDAO {
    suspend fun getAll(): List<Event>

    suspend fun getById(id: String): Event?

    suspend fun create(event: Event): Event

    suspend fun update(event: Event): Event

    suspend fun delete(id: String): Boolean
}
