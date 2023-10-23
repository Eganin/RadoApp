import ktor.KtorSharedRequestsRemoteDataSource
import mapper.UnconfirmedRequestInfoItemMapper
import models.FullUnconfirmedRequestResponse
import models.UnconfirmedRequestInfoItem
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton
import other.Mapper

val sharedRequestsModule = DI.Module(name = "sharedRequestsModule") {
    bind<KtorSharedRequestsRemoteDataSource>() with provider {
        KtorSharedRequestsRemoteDataSource(httpClient = instance())
    }

    bind<Mapper<FullUnconfirmedRequestResponse, UnconfirmedRequestInfoItem>>() with singleton {
        UnconfirmedRequestInfoItemMapper()
    }

    bind<UnconfirmedRequestsRepository>() with singleton {
        UnconfirmedRequestsRepositoryImpl(
            remoteDataSource = instance(),
            unconfirmedRequestInfoItemMapper = instance()
        )
    }
}