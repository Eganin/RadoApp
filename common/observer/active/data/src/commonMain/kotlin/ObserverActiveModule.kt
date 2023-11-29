import ktor.KtorObserverActiveRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val observerActiveModule = DI.Module(name = "observerActiveModule") {
    bind<KtorObserverActiveRemoteDataSource>() with singleton {
        KtorObserverActiveRemoteDataSource(httpClient = instance())
    }

    bind<ActiveRequestsForObserverRepository>() with singleton {
        ActiveRequestsForObserverRepositoryImpl(
            remoteDataSource = instance()
        )
    }
}