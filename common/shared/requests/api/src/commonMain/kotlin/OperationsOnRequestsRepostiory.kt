import models.RecreateRequestResponse
import other.WrapperForResponse

interface OperationsOnRequestsRepostiory {
    suspend fun removeRequest(requestId: Int): WrapperForResponse

    suspend fun recreateRequest(
        requestId: Int,
        driverId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String
    ): RecreateRequestResponse
}