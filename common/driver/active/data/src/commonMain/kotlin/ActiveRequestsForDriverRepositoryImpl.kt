import models.ActiveRequestsForDriverItem
import models.CreateRequestIdItem
import settings.SettingsAuthDataSource

class ActiveRequestsForDriverRepositoryImpl(
    private val localDataSource : SettingsAuthDataSource
): ActiveRequestsForDriverRepository {
    override suspend fun createRequest(
        typeVehicle: String,
        numberVehicle: String,
        faultDescription: String
    ): CreateRequestIdItem {
        TODO("Not yet implemented")
    }

    override suspend fun getRequestsByDate(date: String): ActiveRequestsForDriverItem {
        TODO("Not yet implemented")
    }

    override suspend fun getRequests(): ActiveRequestsForDriverItem {
        TODO("Not yet implemented")
    }
}