import ktor.KtorSharedRequestsRemoteDataSource
import models.UnconfirmedRequestInfoItem
import org.company.rado.core.MainRes

class UnconfirmedRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource
) : UnconfirmedRequestsRepository {
    override suspend fun getInfoForUnconfirmedRequest(requestId: Int): UnconfirmedRequestInfoItem {
        val unconfirmedRequestInfoItem = try {
            val unconfirmedRequestInfo =
                remoteDataSource.fetchUnconfirmedRequestInfo(requestId = requestId)
            UnconfirmedRequestInfoItem.Success(requestInfo = unconfirmedRequestInfo)
        } catch (e: Exception) {
            UnconfirmedRequestInfoItem.Error(message = MainRes.string.unconfirmed_request_info_is_not_fetch)
        }

        return unconfirmedRequestInfoItem
    }
}