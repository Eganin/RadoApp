import io.github.aakira.napier.log
import io.ktor.http.HttpStatusCode
import ktor.KtorSharedRequestsRemoteDataSource
import models.RecreateRequestItem
import org.company.rado.core.MainRes
import other.Mapper
import other.WrapperForResponse
import settings.SettingsAuthDataSource

class OperationsOnRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val localDataSource: SettingsAuthDataSource,
    private val httpStatusCodeMapper: Mapper<HttpStatusCode, WrapperForResponse>
) : OperationsOnRequestsRepository {
    override suspend fun removeRequest(requestId: Int): WrapperForResponse {
        return try {
            val statusCode = remoteDataSource.deleteRemoveRequest(requestId = requestId)
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Error remove request" }
            WrapperForResponse.Failure(message = MainRes.string.remove_request_error)
        }
    }

    override suspend fun recreateRequest(
        requestId: Int,
        typeVehicle: String,
        numberVehicle: String,
        oldTypeVehicle: String,
        oldNumberVehicle: String,
        faultDescription: String
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
                faultDescription = faultDescription
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