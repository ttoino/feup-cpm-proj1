package pt.up.fe.cpm.tiktek.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.local.LocalCartRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstCafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstEventsRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstUserRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindCafeteriaRepository(repo: LocalFirstCafeteriaRepository): CafeteriaRepository

    @Binds
    abstract fun bindCartRepository(repo: LocalCartRepository): CartRepository

    @Binds
    abstract fun bindEventsRepository(repo: LocalFirstEventsRepository): EventsRepository

    @Binds
    abstract fun bindUserRepository(repo: LocalFirstUserRepository): UserRepository
}
