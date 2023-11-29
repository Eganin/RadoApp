import models.ActiveRequestsForObserverItem

interface ActiveRequestsForObserverRepository {

    suspend fun getRequestsByDate(date:String):ActiveRequestsForObserverItem
}