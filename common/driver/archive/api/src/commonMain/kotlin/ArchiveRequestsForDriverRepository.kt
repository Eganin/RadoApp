import models.ArchiveRequestsForDriverItem

interface ArchiveRequestsForDriverRepository {

    suspend fun getArchiveRequests():ArchiveRequestsForDriverItem
}