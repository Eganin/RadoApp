import models.UnconfirmedRequestsForDriverItem

interface UnconfirmedRequestsForDriverRepository {
    suspend fun getRequests(): UnconfirmedRequestsForDriverItem
}