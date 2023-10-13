import models.CreateRequestIdItem

interface ActiveRequestsForDriverRepository {

    suspend fun createRequest(typeVehicle:String,numberVehicle:String,faultDescription:String): CreateRequestIdItem

    suspend fun getRequestByDate(date: String)
}