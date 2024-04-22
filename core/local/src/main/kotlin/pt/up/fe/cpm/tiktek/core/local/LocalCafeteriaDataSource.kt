package pt.up.fe.cpm.tiktek.core.local

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem

interface LocalCafeteriaDataSource {
    fun getCafeteriaItems(): Flow<List<CafeteriaItem>>

    fun getCafeteriaItem(id: String): Flow<CafeteriaItem>

    suspend fun insert(items: List<CafeteriaItem>)
}
