package pt.up.fe.cpm.tiktek.core.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.local.datastore.DataStoreAuthenticationTokenDataStore

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    abstract fun bindLocalAuthenticationTokenDataSource(repo: DataStoreAuthenticationTokenDataStore): LocalAuthenticationTokenDataSource
}
