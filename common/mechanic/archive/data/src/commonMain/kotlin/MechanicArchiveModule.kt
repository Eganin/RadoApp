import ktor.KtorMechanicArchiveRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val mechanicArchiveModule = DI.Module(name="mechanicArchiveModule"){
    bind<KtorMechanicArchiveRemoteDataSource>() with singleton {
        KtorMechanicArchiveRemoteDataSource(httpClient = instance())
    }

    bind<ArchiveRequestsForMechanicRepository>() with singleton {
        ArchiveRequestsForMechanicRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance()
        )
    }
}