import models.ActiveRequestsForMechanicItem
import other.WrapperForResponse
import settings.SettingsAuthDataSource

class ActiveRequestsForMechanicRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
): ActiveRequestsForMechanicRepository {
    override suspend fun getRequestsByDate(date: String): ActiveRequestsForMechanicItem {
        TODO("Not yet implemented")
    }

    override suspend fun archieveRequest(requestId: Int): WrapperForResponse {
        TODO("Not yet implemented")
    }
}