import models.RecreateRequestItem
import other.WrapperForResponse

interface OperationsOnRequestsRepository {
    suspend fun removeRequest(requestId: Int): WrapperForResponse

    suspend fun recreateRequest(
        requestId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String
    ): RecreateRequestItem
}