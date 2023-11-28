import models.ActiveRequestsForMechanicItem
import other.WrapperForResponse

interface ActiveRequestsForMechanicRepository {

    suspend fun getRequestsByDate(date:String):ActiveRequestsForMechanicItem

    suspend fun archieveRequest(requestId:Int):WrapperForResponse
}