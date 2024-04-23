package pt.up.fe.cpm.tiktek.core.local.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.local.LocalAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalCafeteriaDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalCartDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalEventsDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalTicketsDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalUserDataSource
import pt.up.fe.cpm.tiktek.core.local.LocalVouchersDataSource
import pt.up.fe.cpm.tiktek.core.local.datastore.DataStoreAuthenticationTokenDataSource
import pt.up.fe.cpm.tiktek.core.local.datastore.DataStoreCartDataSource
import pt.up.fe.cpm.tiktek.core.local.datastore.DataStoreUserDataSource
import pt.up.fe.cpm.tiktek.core.local.room.RoomCafeteriaDataSource
import pt.up.fe.cpm.tiktek.core.local.room.RoomEventsDataSource
import pt.up.fe.cpm.tiktek.core.local.room.RoomTicketsDataSource
import pt.up.fe.cpm.tiktek.core.local.room.RoomVouchersDataSource

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalModule {
    @Binds
    abstract fun bindLocalAuthenticationTokenDataSource(source: DataStoreAuthenticationTokenDataSource): LocalAuthenticationTokenDataSource

    @Binds
    abstract fun bindLocalCafeteriaDataSource(source: RoomCafeteriaDataSource): LocalCafeteriaDataSource

    @Binds
    abstract fun bindLocalCartDataSource(source: DataStoreCartDataSource): LocalCartDataSource

    @Binds
    abstract fun bindLocalEventsDataSource(source: RoomEventsDataSource): LocalEventsDataSource

    @Binds
    abstract fun bindLocalTicketsDataSource(source: RoomTicketsDataSource): LocalTicketsDataSource

    @Binds
    abstract fun bindLocalUserDataSource(source: DataStoreUserDataSource): LocalUserDataSource

    @Binds
    abstract fun bindLocalVouchersDataSource(source: RoomVouchersDataSource): LocalVouchersDataSource
}
