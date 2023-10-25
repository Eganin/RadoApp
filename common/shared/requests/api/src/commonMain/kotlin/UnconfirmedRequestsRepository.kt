import models.UnconfirmedRequestInfoItem
import models.UnconfirmedRequestsItem

interface UnconfirmedRequestsRepository {

    suspend fun getInfoForUnconfirmedRequest(requestId:Int): UnconfirmedRequestInfoItem

    suspend fun getRequests(isDriver:Boolean): UnconfirmedRequestsItem
}