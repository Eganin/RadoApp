import models.FullRejectRequestItem

interface RejectRequestsRepository {

    suspend fun getRejectRequestInfo(requestId: Int): FullRejectRequestItem
}