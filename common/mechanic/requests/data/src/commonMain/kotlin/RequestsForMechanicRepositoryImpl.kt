import io.ktor.http.HttpStatusCode
import ktor.KtorRequestsMechanicRemoteDataSource
import ktor.models.ConfirmationRequestRemote
import ktor.models.RejectRequestRemote
import org.company.rado.core.MainRes
import other.Mapper
import other.WrapperForResponse
import settings.SettingsAuthDataSource

class RequestsForMechanicRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorRequestsMechanicRemoteDataSource,
    private val httpStatusCodeMapper: Mapper<HttpStatusCode, WrapperForResponse>
) : RequestsForMechanicRepository {
    override suspend fun confirmationRequest(
        requestId: Int,
        date: String,
        time: String,
        streetRepair:String
    ): WrapperForResponse {
        return try {
            val mechanicId = localDataSource.fetchLoginUserInfo().userId
            val statusCode = remoteDataSource.confirmationRequest(
                request = ConfirmationRequestRemote(
                    requestId = requestId,
                    time = time,
                    date = date,
                    mechanicId = mechanicId,
                    streetRepair=streetRepair
                )
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            WrapperForResponse.Failure(message = MainRes.string.confirm_request_error_message)
        }
    }

    override suspend fun rejectRequest(
        requestId: Int,
        commentMechanic: String
    ): WrapperForResponse {
        return try {
            val mechanicId = localDataSource.fetchLoginUserInfo().userId
            val statusCode = remoteDataSource.rejectRequest(
                request = RejectRequestRemote(
                    requestId = requestId,
                    mechanicId = mechanicId,
                    commentMechanic = commentMechanic
                )
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            WrapperForResponse.Failure(message = MainRes.string.reject_request_error_message)
        }
    }
}