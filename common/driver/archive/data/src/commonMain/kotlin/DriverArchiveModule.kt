import ktor.KtorDriverArchiveRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val driverArchiveModule = DI.Module(name = "driverArchiveModiule") {
    bind<KtorDriverArchiveRemoteDataSource>() with singleton {
        KtorDriverArchiveRemoteDataSource(httpClient = instance())
    }

    bind<ArchiveRequestsForDriverRepository>() with singleton {
        ArchiveRequestsForDriverRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance()
        )
    }
}