package pt.up.fe.cpm.tiktek.backend.di

import dagger.Component
import io.ktor.server.application.Application
import pt.up.fe.cpm.tiktek.core.database.CafeteriaItemDAO
import pt.up.fe.cpm.tiktek.core.database.EventDAO
import pt.up.fe.cpm.tiktek.core.database.OrderDAO
import pt.up.fe.cpm.tiktek.core.database.TicketDAO
import pt.up.fe.cpm.tiktek.core.database.UserDAO
import pt.up.fe.cpm.tiktek.core.database.VoucherDAO
import pt.up.fe.cpm.tiktek.core.database.di.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val cafeteriaItem: CafeteriaItemDAO
    val event: EventDAO
    val order: OrderDAO
    val ticket: TicketDAO
    val user: UserDAO
    val voucher: VoucherDAO
}

val Application.database: DatabaseComponent
    get() = DaggerDatabaseComponent.create()
