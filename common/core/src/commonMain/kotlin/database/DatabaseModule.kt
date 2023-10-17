package database

import org.kodein.di.DI

//import di.Inject
//import org.company.rado.Database
//import org.kodein.di.DI
//import org.kodein.di.bind
//import org.kodein.di.singleton


internal val databaseModule = DI.Module("databaseModule") {
//    bind<DriverFactory>() with singleton {
//        DriverFactory(Inject.instance())
//    }
//
//    bind<Database>() with singleton {
//        val driverFactory = Inject.instance<DriverFactory>()
//        val driver = driverFactory.createDriver(name="rado.db")
//        Database(driver!!)
//    }
}