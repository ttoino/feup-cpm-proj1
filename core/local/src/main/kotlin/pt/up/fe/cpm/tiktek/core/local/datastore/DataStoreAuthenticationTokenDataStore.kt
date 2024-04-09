package pt.up.fe.cpm.tiktek.core.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("authentication")

private val KEY_TOKEN = stringPreferencesKey("token")

@Singleton
class DataStoreAuthenticationTokenDataStore
    @Inject
    constructor(
        @ApplicationContext
        private val context: Context,
    ) : LocalAuthenticationTokenDataSource {
        override fun token() = context.dataStore.data.map { it[KEY_TOKEN] }

        override suspend fun setToken(token: String?) {
            context.dataStore.edit { preferences ->
                if (token == null) {
                    preferences.remove(KEY_TOKEN)
                } else {
                    preferences[KEY_TOKEN] = token
                }
            }
        }
    }
