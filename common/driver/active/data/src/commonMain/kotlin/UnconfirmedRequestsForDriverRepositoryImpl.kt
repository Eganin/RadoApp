import ktor.KtorDriverActiveRemoteDataSource
import settings.SettingsAuthDataSource

class UnconfirmedRequestsForDriverRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorDriverActiveRemoteDataSource,
) {
}