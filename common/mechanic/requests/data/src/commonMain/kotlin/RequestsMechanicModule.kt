import ktor.KtorRequestsMechanicRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val requestsMechanicModule = DI.Module(name = "requestsMechanicModule") {
    bind<KtorRequestsMechanicRemoteDataSource>() with provider {
        KtorRequestsMechanicRemoteDataSource(httpClient = instance())
    }

    bind<RequestsForMechanicRepository>() with singleton {
        RequestsForMechanicRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance()
        )
    }
}