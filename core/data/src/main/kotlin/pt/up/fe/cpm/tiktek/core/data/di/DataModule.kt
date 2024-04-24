package pt.up.fe.cpm.tiktek.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pt.up.fe.cpm.tiktek.core.data.CafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.CafeteriaTerminalRepository
import pt.up.fe.cpm.tiktek.core.data.CartRepository
import pt.up.fe.cpm.tiktek.core.data.EventsRepository
import pt.up.fe.cpm.tiktek.core.data.OrdersRepository
import pt.up.fe.cpm.tiktek.core.data.TicketsRepository
import pt.up.fe.cpm.tiktek.core.data.TicketsTerminalRepository
import pt.up.fe.cpm.tiktek.core.data.UserRepository
import pt.up.fe.cpm.tiktek.core.data.VouchersRepository
import pt.up.fe.cpm.tiktek.core.data.local.LocalCartRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstCafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstEventsRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstOrdersRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstTicketsRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstUserRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstVouchersRepository
import pt.up.fe.cpm.tiktek.core.data.remote.RemoteCafeteriaTerminalRepository
import pt.up.fe.cpm.tiktek.core.data.remote.RemoteTicketsTerminalRepository

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
    abstract fun bindOrdersRepository(repo: LocalFirstOrdersRepository): OrdersRepository

    @Binds
    abstract fun bindTicketsRepository(repo: LocalFirstTicketsRepository): TicketsRepository

    @Binds
    abstract fun bindUserRepository(repo: LocalFirstUserRepository): UserRepository

    @Binds
    abstract fun bindVouchersRepository(repo: LocalFirstVouchersRepository): VouchersRepository

    @Binds
    abstract fun bindCafeteriaTerminalRepository(repo: RemoteCafeteriaTerminalRepository): CafeteriaTerminalRepository

    @Binds
    abstract fun bindTicketsTerminalRepository(repo: RemoteTicketsTerminalRepository): TicketsTerminalRepository
}
