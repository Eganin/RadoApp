import models.RecreateRequestItem

interface OperationsOnRequestsRepository {
    suspend fun recreateRequest(
        requestId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String,
    ): RecreateRequestItem
}