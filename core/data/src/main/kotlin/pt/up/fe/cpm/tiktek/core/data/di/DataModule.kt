package pt.up.fe.cpm.tiktek.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstCafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstUserRepository
import pt.up.fe.cpm.tiktek.core.data.remote.RemoteEventsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindCafeteriaRepository(repo: LocalFirstCafeteriaRepository): CafeteriaRepository

    @Binds
    abstract fun bindEventsRepository(repo: RemoteEventsRepository): EventsRepository

    @Binds
    abstract fun bindUserRepository(repo: LocalFirstUserRepository): UserRepository
}
