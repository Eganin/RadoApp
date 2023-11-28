import ktor.KtorMechanicActiveRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val mechanicActiveModule = DI.Module(name="mechanicActiveModule"){
    bind<KtorMechanicActiveRemoteDataSource>() with singleton {
        KtorMechanicActiveRemoteDataSource(httpClient = instance())
    }

    bind<ActiveRequestsForMechanicRepository>() with singleton {
        ActiveRequestsForMechanicRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance(),
            httpStatusCodeMapper = instance()
        )
    }
}