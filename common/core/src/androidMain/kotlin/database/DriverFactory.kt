package database
//
//import app.cash.sqldelight.db.SqlDriver
//import app.cash.sqldelight.driver.android.AndroidSqliteDriver
//import org.company.rado.Database
//import platform.PlatformConfiguration
//
//actual class DriverFactory actual constructor(private val platformConfiguration: PlatformConfiguration) {
//
//    actual fun createDriver(name: String): SqlDriver? {
//        return AndroidSqliteDriver(Database.Schema, platformConfiguration.androidContext, name)
//    }
//}