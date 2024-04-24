package pt.up.fe.cpm.tiktek.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import pt.up.fe.cpm.tiktek.core.data.local.LocalCartRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstCafeteriaRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstEventsRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstOrdersRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstTicketsRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstUserRepository
import pt.up.fe.cpm.tiktek.core.data.localfirst.LocalFirstVouchersRepository
import pt.up.fe.cpm.tiktek.core.data.work.Deletable
import pt.up.fe.cpm.tiktek.core.data.work.Syncable

@Module
@InstallIn(SingletonComponent::class)
abstract class WorkModule {
    @Binds
    @IntoSet
    abstract fun bindSyncableCafeteriaRepository(cafeteriaRepository: LocalFirstCafeteriaRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindSyncableEventsRepository(eventsRepository: LocalFirstEventsRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindSyncableOrdersRepository(ordersRepository: LocalFirstOrdersRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindSyncableTicketsRepository(ticketsRepository: LocalFirstTicketsRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindSyncableUserRepository(userRepository: LocalFirstUserRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindSyncableVouchersRepository(vouchersRepository: LocalFirstVouchersRepository): Syncable

    @Binds
    @IntoSet
    abstract fun bindDeletableCartRepository(cartRepository: LocalCartRepository): Deletable

    @Binds
    @IntoSet
    abstract fun bindDeletableOrdersRepository(ordersRepository: LocalFirstOrdersRepository): Deletable

    @Binds
    @IntoSet
    abstract fun bindDeletableTicketsRepository(ticketsRepository: LocalFirstTicketsRepository): Deletable

    @Binds
    @IntoSet
    abstract fun bindDeletableUserRepository(userRepository: LocalFirstUserRepository): Deletable

    @Binds
    @IntoSet
    abstract fun bindDeletableVouchersRepository(vouchersRepository: LocalFirstVouchersRepository): Deletable
}
