package pt.up.fe.cpm.tiktek.core.database.di

import dagger.Binds
import dagger.Module
import pt.up.fe.cpm.tiktek.core.database.CafeteriaItemDAO
import pt.up.fe.cpm.tiktek.core.database.EventDAO
import pt.up.fe.cpm.tiktek.core.database.OrderDAO
import pt.up.fe.cpm.tiktek.core.database.TicketDAO
import pt.up.fe.cpm.tiktek.core.database.UserDAO
import pt.up.fe.cpm.tiktek.core.database.VoucherDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedCafeteriaItemDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedEventDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedOrderDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedTicketDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedUserDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedVoucherDAO

@Module
abstract class DatabaseModule {
    @Binds
    abstract fun bindCafeteriaItemDAO(dao: ExposedCafeteriaItemDAO): CafeteriaItemDAO

    @Binds
    abstract fun bindEventDAO(dao: ExposedEventDAO): EventDAO

    @Binds
    abstract fun bindOrderDAO(dao: ExposedOrderDAO): OrderDAO

    @Binds
    abstract fun bindTicketDAO(dao: ExposedTicketDAO): TicketDAO

    @Binds
    abstract fun bindUserDAO(dao: ExposedUserDAO): UserDAO

    @Binds
    abstract fun bindVoucherDAO(dao: ExposedVoucherDAO): VoucherDAO
}
