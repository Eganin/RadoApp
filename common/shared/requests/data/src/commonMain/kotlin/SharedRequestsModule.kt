import ktor.KtorSharedRequestsRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton

val sharedRequestsModule = DI.Module(name = "sharedRequestsModule") {
    bind<KtorSharedRequestsRemoteDataSource>() with provider {
        KtorSharedRequestsRemoteDataSource(httpClient = instance())
    }

    bind<UnconfirmedRequestsRepository>() with singleton {
        UnconfirmedRequestsRepositoryImpl(
            remoteDataSource = instance()
        )
    }
}