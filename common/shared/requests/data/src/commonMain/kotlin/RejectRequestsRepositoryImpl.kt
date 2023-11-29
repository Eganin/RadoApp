import ktor.KtorSharedRequestsRemoteDataSource
import models.FullRejectRequestItem
import models.FullRejectRequestResponse
import org.company.rado.core.MainRes
import other.Mapper

internal class RejectRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val mapper: Mapper<FullRejectRequestResponse, FullRejectRequestItem>
) : RejectRequestsRepository {
    override suspend fun getRejectRequestInfo(requestId: Int): FullRejectRequestItem {
        val fullRequestItem = try {
            val fullRequest = remoteDataSource.fetchRejectRequestInfo(requestId = requestId)
            mapper.map(source = fullRequest)
        } catch (e: Exception) {
            FullRejectRequestItem.Error(message = MainRes.string.active_request_info_failure)
        }

        return fullRequestItem
    }

}