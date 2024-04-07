package pt.up.fe.cpm.tiktek.backend.di

import dagger.Component
import io.ktor.server.application.Application
import pt.up.fe.cpm.tiktek.core.database.EventDAO
import pt.up.fe.cpm.tiktek.core.database.UserDAO
import pt.up.fe.cpm.tiktek.core.database.di.DatabaseModule
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    val event: EventDAO
    val user: UserDAO
}

val Application.database: DatabaseComponent
    get() = DaggerDatabaseComponent.create()
