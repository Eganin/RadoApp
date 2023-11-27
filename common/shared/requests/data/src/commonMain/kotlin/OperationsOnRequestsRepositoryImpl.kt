import ktor.KtorSharedRequestsRemoteDataSource
import models.RecreateRequestItem
import org.company.rado.core.MainRes
import settings.SettingsAuthDataSource

class OperationsOnRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val localDataSource: SettingsAuthDataSource
) : OperationsOnRequestsRepository {

    override suspend fun recreateRequest(
        requestId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String,
        arrivalDate:String
    ): RecreateRequestItem {
        return try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.updateRecreateRequest(
                requestId = requestId,
                driverId = userInfo.userId,
                typeVehicle = typeVehicle,
                numberVehicle = numberVehicle,
                oldTypeVehicle = oldTypeVehicle,
                oldNumberVehicle = oldNumberVehicle,
                faultDescription = faultDescription,
                arrivalDate=arrivalDate
            )
            RecreateRequestItem.Success(response = response)
        } catch (e: Exception) {
            RecreateRequestItem.Error(message = MainRes.string.recreate_request_error)
        }
    }

    private companion object {
        const val TAG = "OperationsOnRequestsRepository"
    }
}