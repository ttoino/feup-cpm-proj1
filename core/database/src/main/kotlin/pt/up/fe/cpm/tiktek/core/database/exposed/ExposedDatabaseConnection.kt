package pt.up.fe.cpm.tiktek.core.database.exposed

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExposedDatabaseConnection
    @Inject
    constructor() {
        private val database: Database

        init {
            val driverClassName = "org.h2.Driver"
            val jdbcURL = "jdbc:h2:file:./build/db"
            database = Database.connect(jdbcURL, driverClassName)
            transaction(database) {
                SchemaUtils.createMissingTablesAndColumns(Events)
            }
        }

        suspend fun <T> query(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO, database) { block() }
    }
