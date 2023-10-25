import ktor.KtorSharedRequestsRemoteDataSource
import models.FullRequestItem
import models.FullRequestResponse
import org.company.rado.core.MainRes
import other.Mapper

class ActiveRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val mapper: Mapper<FullRequestResponse, FullRequestItem>
) : ActiveRequestsRepository {
    override suspend fun getActiveRequestInfo(requestId: Int): FullRequestItem {
        val fullRequestItem = try {
            val fullRequest = remoteDataSource.fetchActiveRequestInfo(requestId = requestId)
            mapper.map(source = fullRequest)
        } catch (e: Exception) {
            FullRequestItem.Error(message = MainRes.string.active_request_info_failure)
        }

        return fullRequestItem
    }
}