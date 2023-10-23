import ktor.KtorAuthRemoteDataSource
import mapper.LoginInfoMapper
import mapper.UserIdMapper
import models.LoginInfoItem
import models.LoginInfoResponse
import models.UserIdItem
import models.UserIdResponse
import org.kodein.di.*
import other.Mapper
import settings.SettingsAuthDataSource

val authModule = DI.Module(name = "authModule") {
    bind<SettingsAuthDataSource>() with provider {
        SettingsAuthDataSource(settings = instance())
    }

    bind<KtorAuthRemoteDataSource>() with provider {
        KtorAuthRemoteDataSource(httpClient = instance())
    }

    bind<Mapper<UserIdResponse, UserIdItem>>() with singleton {
        UserIdMapper()
    }

    bind<Mapper<LoginInfoResponse, LoginInfoItem>>() with singleton {
        LoginInfoMapper()
    }

    bind<AuthRepository>() with singleton {
        AuthRepositoryImpl(
            remoteDataSource = instance(),
            localDataSource = instance(),
            userIdMapper = instance(),
            loginInfoMapper = instance()
        )
    }
}