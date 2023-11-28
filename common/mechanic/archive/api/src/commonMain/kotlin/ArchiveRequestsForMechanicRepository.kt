import models.SmallArchiveRequestForMechanic

interface ArchiveRequestsForMechanicRepository {

    suspend fun getArchiveRequests():List<SmallArchiveRequestForMechanic>
}