import models.UnconfirmedRequestInfoItem

interface UnconfirmedRequestsRepository {

    suspend fun getInfoForUnconfirmedRequest(requestId:Int): UnconfirmedRequestInfoItem
}