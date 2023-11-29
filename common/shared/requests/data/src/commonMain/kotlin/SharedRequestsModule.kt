import ktor.KtorSharedRequestsRemoteDataSource
import mapper.FullRequestItemMapper
import mapper.UnconfirmedRequestInfoItemMapper
import models.FullRequestItem
import models.FullRequestResponse
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
            unconfirmedRequestInfoItemMapper = instance(),
            localDataSource = instance()
        )
    }

    bind<Mapper<FullRequestResponse, FullRequestItem>>() with singleton {
        FullRequestItemMapper()
    }

    bind<ActiveRequestsRepository>() with singleton {
        ActiveRequestsRepositoryImpl(
            remoteDataSource = instance(),
            mapper = instance()
        )
    }

    bind<ArchiveRequestsRepository>() with singleton {
        ArchiveRequestsRepositoryImpl(
            remoteDataSource = instance(),
            mapper = instance()
        )
    }

    bind<RejectRequestsRepository>() with singleton {
        RejectRequestsRepositoryImpl(
            remoteDataSource = instance(),
            mapper = instance()
        )
    }

    bind<OperationsOnRequestsRepository>() with singleton {
        OperationsOnRequestsRepositoryImpl(
            remoteDataSource = instance(),
            localDataSource = instance()
        )
    }
}