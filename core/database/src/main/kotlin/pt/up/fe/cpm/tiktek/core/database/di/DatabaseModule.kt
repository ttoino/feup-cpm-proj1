package pt.up.fe.cpm.tiktek.core.database.di

import dagger.Binds
import dagger.Module
import pt.up.fe.cpm.tiktek.core.database.EventDAO
import pt.up.fe.cpm.tiktek.core.database.exposed.ExposedEventDAO

@Module
abstract class DatabaseModule {
    @Binds
    abstract fun bindEventDAO(dao: ExposedEventDAO): EventDAO
}
