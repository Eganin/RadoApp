import models.SmallRejectRequestItem

interface RejectRequestsRepositoryForDriverAndObserver {

    suspend fun getRejectRequestsForObserver():SmallRejectRequestItem

    suspend fun getRejectRequestsForDriver(driverId:Int):SmallRejectRequestItem
}