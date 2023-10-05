package database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.worker.WebWorkerDriver
import org.w3c.dom.Worker
import platform.PlatformConfiguration

actual class ProvideDbDriver actual constructor(
    private val schema: SqlSchema<QueryResult.AsyncValue<Unit>>,
    private val platformConfiguration: PlatformConfiguration,
    private val name:String
){
    actual suspend fun provideDbDriver(): SqlDriver{
        return WebWorkerDriver(
            Worker(
                js("""new URL("@cashapp/sqldelight-sqljs-worker/sqljs.worker.js", import.meta.url)""")
            )
        ).also { schema.create(it).await() }
    }
}