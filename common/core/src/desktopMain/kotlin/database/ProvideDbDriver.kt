package database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import platform.PlatformConfiguration
import java.io.File

actual class ProvideDbDriver actual constructor(
    private val schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    private val platformConfiguration: PlatformConfiguration,
    private val name:String
){
    actual suspend fun provideDbDriver(): SqlDriver{
        val appPath =platformConfiguration.appDataPath
        if (!File(appPath).exists()) {
            File(appPath).mkdirs()
        }

        val filePath = "$appPath/$name"
        return JdbcSqliteDriver("jdbc:sqlite:$filePath").apply {
            if (!File(filePath).exists()) {
                schema.create(this).await()
            }
        }
    }
}