import models.FullRequestItem

interface ActiveRequestsRepository {

    suspend fun getActiveRequestInfo(requestId:Int): FullRequestItem
}