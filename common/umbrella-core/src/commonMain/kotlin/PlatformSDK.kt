import di.Inject
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.github.aakira.napier.log
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.direct
import org.kodein.di.singleton
import platform.PlatformConfiguration

object PlatformSDK {

    fun init(platformConfiguration: PlatformConfiguration) {
        //init logger
        Napier.base(DebugAntilog())

        val platformModule = DI.Module(name = "platformModule") {
            bind<PlatformConfiguration>() with singleton {
                platformConfiguration
            }
        }
        // init di
        Inject.createDependencies(tree = DI {
            importAll(
                platformModule,
                coreModule,
                authModule,
                driverActiveModule,
                driverArchiveModule,
                sharedRequestsModule,
                requestsMechanicModule,
                mechanicActiveModule,
                mechanicArchiveModule
            )
        }.direct)

        log(tag ="DI") { "create di" }
    }
}