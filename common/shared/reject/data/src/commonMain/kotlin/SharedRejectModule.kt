import ktor.KtorSharedRejectRemoteDataSource
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val sharedRejectModule= DI.Module(name="sharedRejectModule"){
    bind<KtorSharedRejectRemoteDataSource>() with singleton {
        KtorSharedRejectRemoteDataSource(httpClient = instance())
    }

    bind<RejectRequestsRepositoryForDriverAndObserver>() with singleton {
        RejectRequestsRepositoryForDriverAndObserverImpl(
            localDataSource = instance(),
            remoteDataSource = instance()
        )
    }
}