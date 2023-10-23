import ktor.KtorDriverActiveRemoteDataSource
import ktor.models.KtorUnconfirmedRequest
import models.UnconfirmedRequestsForDriverItem
import org.company.rado.core.MainRes
import settings.SettingsAuthDataSource

class UnconfirmedRequestsForDriverRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorDriverActiveRemoteDataSource,
) : UnconfirmedRequestsForDriverRepository {
    override suspend fun getRequests(): UnconfirmedRequestsForDriverItem {
        val unconfirmedRequestsItem = try {
            val userId = localDataSource.fetchLoginUserInfo().userId
            val unconfirmedRequests =
                remoteDataSource.fetchUnconfirmedRequests(request = KtorUnconfirmedRequest(userId = userId))
            UnconfirmedRequestsForDriverItem.Success(items = unconfirmedRequests)
        } catch (e: Exception) {
            UnconfirmedRequestsForDriverItem.Error(message = MainRes.string.unconfirmed_requests_is_not_fetch)
        }

        return unconfirmedRequestsItem
    }
}