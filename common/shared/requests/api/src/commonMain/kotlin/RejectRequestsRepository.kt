import models.FullRequestItem

interface RejectRequestsRepository {

    suspend fun getRejectRequestInfo(requestId:Int): FullRequestItem
}