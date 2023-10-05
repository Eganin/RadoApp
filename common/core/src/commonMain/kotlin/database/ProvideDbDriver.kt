package database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import platform.PlatformConfiguration

expect class ProvideDbDriver(
    schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    platformConfiguration: PlatformConfiguration,
    name:String
){
    suspend fun provideDbDriver(): SqlDriver
}