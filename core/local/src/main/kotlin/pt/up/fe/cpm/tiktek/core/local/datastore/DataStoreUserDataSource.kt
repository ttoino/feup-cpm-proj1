package pt.up.fe.cpm.tiktek.core.local.datastore

import android.content.Context
import androidx.datastore.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import pt.up.fe.cpm.tiktek.core.local.LocalUserDataSource
import pt.up.fe.cpm.tiktek.core.model.User
import javax.inject.Inject

private val Context.dataStore by dataStore("user", serializer<User?>(null))

class DataStoreUserDataSource
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : LocalUserDataSource {
        override fun user(): Flow<User?> = context.dataStore.data

        override suspend fun update(user: User) {
            context.dataStore.updateData {
                user
            }
        }

        override suspend fun delete() {
            context.dataStore.updateData { null }
        }
    }
