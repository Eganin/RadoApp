import models.FullRequestItem

interface ArchiveRequestsRepository {

    suspend fun getArchiveRequestInfo(requestId:Int):FullRequestItem
}