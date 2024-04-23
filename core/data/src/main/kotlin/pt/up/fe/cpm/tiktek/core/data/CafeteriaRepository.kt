package pt.up.fe.cpm.tiktek.core.data

import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.model.CafeteriaItem

interface CafeteriaRepository {
    fun getCafeteriaItems(): Flow<List<CafeteriaItem>>

    fun getCafeteriaItem(id: String): Flow<CafeteriaItem>
}
