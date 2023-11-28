import models.ArchiveRequestsForMechanicItem

interface ArchiveRequestsForMechanicRepository {

    suspend fun getArchiveRequests(): ArchiveRequestsForMechanicItem
}