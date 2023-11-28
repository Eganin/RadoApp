import ktor.KtorObserverArchiveRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val observerArchiveModule = DI.Module(name = "observerArchiveModule") {
    bind<KtorObserverArchiveRemoteDataSource>() with singleton {
        KtorObserverArchiveRemoteDataSource(httpClient = instance())
    }

    bind<ArchiveRequestsForObserverRepository>() with singleton {
        ArchiveRequestsForObserverRepositoryImpl(
            remoteDataSource = instance()
        )
    }
}