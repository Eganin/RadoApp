import ktor.KtorSharedRequestsRemoteDataSource
import models.FullUnconfirmedRequestResponse
import models.UnconfirmedRequestInfoItem
import models.UnconfirmedRequestsItem
import org.company.rado.core.MainRes
import other.Mapper
import settings.SettingsAuthDataSource

internal class UnconfirmedRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val localDataSource: SettingsAuthDataSource,
    private val unconfirmedRequestInfoItemMapper: Mapper<FullUnconfirmedRequestResponse, UnconfirmedRequestInfoItem>
) : UnconfirmedRequestsRepository {
    override suspend fun getInfoForUnconfirmedRequest(requestId: Int): UnconfirmedRequestInfoItem {
        val unconfirmedRequestInfoItem = try {
            unconfirmedRequestInfoItemMapper.map(
                source = remoteDataSource.fetchUnconfirmedRequestInfo(requestId = requestId)
            )
        } catch (e: Exception) {
            UnconfirmedRequestInfoItem.Error(message = MainRes.string.unconfirmed_request_info_is_not_fetch)
        }

        return unconfirmedRequestInfoItem
    }

    override suspend fun getRequests(isDriver: Boolean): UnconfirmedRequestsItem {
        val unconfirmedRequestsItem = try {
            val item = if (isDriver) {
                val userId = localDataSource.fetchLoginUserInfo().userId
                UnconfirmedRequestsItem.Success(
                    items = remoteDataSource.fetchUnconfirmedRequestsForDriver(
                        driverId = userId
                    )
                )
            } else {
                UnconfirmedRequestsItem.Success(items = remoteDataSource.fetchUnconfirmedRequestsForMechanic())
            }
            item
        } catch (e: Exception) {
            UnconfirmedRequestsItem.Error(message = MainRes.string.unconfirmed_requests_is_not_fetch)
        }

        return unconfirmedRequestsItem
    }
}