import ktor.KtorDriverActiveRemoteDataSource
import mapper.CreateRequestIdMapper
import models.CreateRequestIdItem
import models.CreateRequestIdResponse
import org.kodein.di.*
import other.Mapper

val driverActiveModule = DI.Module(name = "driverActiveModule") {
    bind<Mapper<CreateRequestIdResponse, CreateRequestIdItem>>() with singleton {
        CreateRequestIdMapper()
    }

    bind<KtorDriverActiveRemoteDataSource>() with provider {
        KtorDriverActiveRemoteDataSource(httpClient = instance())
    }

    bind<ActiveRequestsForDriverRepository>() with singleton {
        ActiveRequestsForDriverRepositoryImpl(
            localDataSource = instance(),
            remoteDataSource = instance(),
            createRequestMapper = instance()
        )
    }
}