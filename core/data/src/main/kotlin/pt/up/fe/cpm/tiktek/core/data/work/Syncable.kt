package pt.up.fe.cpm.tiktek.core.data.work

import pt.up.fe.cpm.tiktek.core.model.NetworkResult

interface Syncable {
    suspend fun sync(): NetworkResult<Unit>
}
