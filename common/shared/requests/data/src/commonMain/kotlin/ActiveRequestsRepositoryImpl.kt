import ktor.KtorSharedRequestsRemoteDataSource
import models.FullRequestItem
import org.company.rado.core.MainRes

class ActiveRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource
) : ActiveRequestsRepository {
    override suspend fun getActiveRequestInfo(requestId: Int): FullRequestItem {
        val fullRequestItem = try {
            val fullRequest = remoteDataSource.fetchActiveRequestInfo(requestId = requestId)
            FullRequestItem.Success(request = fullRequest)
        } catch (e: Exception) {
            FullRequestItem.Error(message = MainRes.string.active_request_info_failure)
        }

        return fullRequestItem
    }
}