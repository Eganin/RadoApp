import models.ArchiveRequestsForObserverItem

interface ArchiveRequestsForObserverRepository {

    suspend fun getArchiveRequests():ArchiveRequestsForObserverItem
}