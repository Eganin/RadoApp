package database

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import platform.PlatformConfiguration

actual class ProvideDbDriver actual constructor(
    private val schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    private val platformConfiguration: PlatformConfiguration,
    private val name: String
) {
    actual suspend fun provideDbDriver(): SqlDriver {

        return AndroidSqliteDriver(schema.synchronous(), platformConfiguration.androidContext, name)
    }
}