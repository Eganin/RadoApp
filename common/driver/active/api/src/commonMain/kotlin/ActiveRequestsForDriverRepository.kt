import models.ActiveRequestsForDriverItem
import models.CreateRequestIdItem

interface ActiveRequestsForDriverRepository {

    suspend fun createRequest(typeVehicle: String, numberVehicle: String, faultDescription: String): CreateRequestIdItem

    suspend fun getRequestsByDate(date: String): ActiveRequestsForDriverItem

    suspend fun createImagesForRequest(requestId:Int,images: List<Pair<String,ByteArray>>)
}