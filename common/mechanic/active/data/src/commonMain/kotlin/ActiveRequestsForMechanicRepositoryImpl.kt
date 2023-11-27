import io.github.aakira.napier.log
import io.ktor.http.HttpStatusCode
import ktor.KtorMechanicActiveRemoteDataSource
import ktor.models.KtorActiveRequest
import models.ActiveRequestsForMechanicItem
import org.company.rado.core.MainRes
import other.Mapper
import other.WrapperForResponse
import settings.SettingsAuthDataSource

internal class ActiveRequestsForMechanicRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorMechanicActiveRemoteDataSource,
    private val httpStatusCodeMapper: Mapper<HttpStatusCode, WrapperForResponse>
) : ActiveRequestsForMechanicRepository {
    override suspend fun getRequestsByDate(date: String): ActiveRequestsForMechanicItem {
        val activeRequestsForMechanicItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.fetchRequestsByDate(
                request = KtorActiveRequest(
                    userId = userInfo.userId,
                    date = date
                )
            )
            ActiveRequestsForMechanicItem.Success(items = response)
        } catch (e: Exception) {
            log(tag= TAG) { "Error occurred for get requests by date: $date" }
            ActiveRequestsForMechanicItem.Error(message= MainRes.string.requests_by_date_is_not_fetch)
        }

        return activeRequestsForMechanicItem
    }

    override suspend fun archieveRequest(requestId: Int): WrapperForResponse {
        val response = try {
            val statusCode = remoteDataSource.archieveRequest(requestId=requestId)
            httpStatusCodeMapper.map(source = statusCode)
        }catch (e:Exception){
            WrapperForResponse.Failure(message = MainRes.string.archieve_request_is_failure)
        }
        return response
    }

    private companion object {
        const val TAG = "ActiveRequestsForMechanicRepository"
    }
}