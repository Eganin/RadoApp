import models.ActiveRequestsForDriverItem
import models.CreateRequestIdItem
import other.WrapperForResponse

interface ActiveRequestsForDriverRepository {

    suspend fun createRequest(typeVehicle: String, numberVehicle: String, faultDescription: String,arrivalDate:String): CreateRequestIdItem

    suspend fun getRequestsByDate(date: String): ActiveRequestsForDriverItem

    suspend fun createResourceForRequest(requestId:Int,resource: Triple<String, Boolean, ByteArray>): WrapperForResponse

    suspend fun createResourceForCache(resource: Triple<String, Boolean, ByteArray>): WrapperForResponse

    suspend fun deleteResourceForCache(resourceName:String): WrapperForResponse

    suspend fun deleteResourcesForRequest(requestId: Int):WrapperForResponse

    suspend fun deleteRequest(requestId: Int): WrapperForResponse

    suspend fun deleteImageByPathForRequest(imagePath: String):WrapperForResponse

    suspend fun deleteVideoByPathForRequest(videoPath:String):WrapperForResponse
}