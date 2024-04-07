package pt.up.fe.cpm.tiktek.core.database

import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem

interface CafeteriaItemDAO {
    suspend fun getAll(): List<CafeteriaItem>

    suspend fun getById(id: String): CafeteriaItem?

    suspend fun create(cafeteriaItem: CafeteriaItem): CafeteriaItem

    suspend fun update(cafeteriaItem: CafeteriaItem): CafeteriaItem

    suspend fun delete(id: String): Boolean
}
