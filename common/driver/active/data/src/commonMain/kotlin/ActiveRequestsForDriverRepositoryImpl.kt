import io.github.aakira.napier.log
import io.ktor.http.HttpStatusCode
import ktor.KtorDriverActiveRemoteDataSource
import ktor.models.KtorActiveRequest
import ktor.models.KtorCreateRequest
import models.ActiveRequestsForDriverItem
import models.CreateRequestIdItem
import models.CreateRequestIdResponse
import org.company.rado.core.MainRes
import other.Mapper
import other.WrapperForResponse
import settings.SettingsAuthDataSource

internal class ActiveRequestsForDriverRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorDriverActiveRemoteDataSource,
    private val createRequestMapper: Mapper<CreateRequestIdResponse, CreateRequestIdItem>,
    private val httpStatusCodeMapper: Mapper<HttpStatusCode, WrapperForResponse>
) : ActiveRequestsForDriverRepository {
    override suspend fun createRequest(
        typeVehicle: String,
        numberVehicle: String,
        faultDescription: String,
        arrivalDate:String
    ): CreateRequestIdItem {
        val createRequestItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.createRequest(
                request = KtorCreateRequest(
                    driverUsername = userInfo.fullName,
                    typeVehicle = typeVehicle,
                    numberVehicle = numberVehicle,
                    faultDescription = faultDescription,
                    arrivalDate=arrivalDate
                )
            )
            createRequestMapper.map(source = response)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for create request" }
            CreateRequestIdItem.Error(message = MainRes.string.request_is_not_create)
        }
        return createRequestItem
    }

    override suspend fun getRequestsByDate(date: String): ActiveRequestsForDriverItem {
        val activeRequestsForDriverItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response =
                remoteDataSource.fetchRequestsByDate(
                    request = KtorActiveRequest(
                        userId = userInfo.userId,
                        date = date
                    )
                )
            ActiveRequestsForDriverItem.Success(items = response)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for get resource by date: $date" }
            ActiveRequestsForDriverItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }
        return activeRequestsForDriverItem
    }

    override suspend fun createResourceForRequest(
        requestId: Int,
        resource: Triple<String, Boolean, ByteArray>
    ): WrapperForResponse {
        return try {
            val statusCode = remoteDataSource.uploadImageOrVideoForRequest(
                requestId = requestId,
                resourcePath = resource.first,
                isImage = resource.second,
                resourceData = resource.third
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for create resource for request" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    override suspend fun createResourceForCache(resource: Triple<String, Boolean, ByteArray>): WrapperForResponse {
        return try {
            val statusCode = remoteDataSource.uploadImageOrVideoForCache(
                resourcePath = resource.first,
                isImage = resource.second,
                resourceData = resource.third
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for create resource" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    override suspend fun deleteResourceForCache(resourceName: String): WrapperForResponse {
        return try {
            val statusCode = remoteDataSource.deleteResourceCache(resourceName = resourceName)
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for delete resource" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    override suspend fun deleteResourcesForRequest(requestId: Int): WrapperForResponse {
        return try {
            val firstHttpStatusCode =
                remoteDataSource.deleteImagesFromRequest(requestId = requestId)
            val secondHttpStatusCode =
                remoteDataSource.deleteVideosFromRequest(requestId = requestId)
            if (firstHttpStatusCode == HttpStatusCode.OK) {
                httpStatusCodeMapper.map(source = firstHttpStatusCode)
            } else if (secondHttpStatusCode == HttpStatusCode.OK) {
                httpStatusCodeMapper.map(source = secondHttpStatusCode)
            } else {
                WrapperForResponse.Failure(message = MainRes.string.base_error_message)
            }
        } catch (e: Exception) {
            log(tag = TAG) { "Error for delete resource" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    override suspend fun deleteRequest(requestId: Int): WrapperForResponse {
        return try {
            val statusCode = remoteDataSource.deleteRequest(requestId = requestId)
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Error for delete request" }
            WrapperForResponse.Failure(message = MainRes.string.remove_request_error)
        }
    }

    override suspend fun deleteImageByPathForRequest(imagePath: String): WrapperForResponse {
        return try {
            val imagePathSplit = imagePath.split("/images/").last()
            val statusCode = remoteDataSource.deleteImageAndVideoByPath(
                isImage = true,
                resourcePath = imagePathSplit
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Delete resource by path failure" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    override suspend fun deleteVideoByPathForRequest(videoPath: String): WrapperForResponse {
        return try {
            val videoUrlSplit = videoPath.split("/videos/").last()
            val statusCode = remoteDataSource.deleteImageAndVideoByPath(
                isImage = false,
                resourcePath = videoUrlSplit
            )
            httpStatusCodeMapper.map(source = statusCode)
        } catch (e: Exception) {
            log(tag = TAG) { "Delete resource by path failure" }
            WrapperForResponse.Failure(message = MainRes.string.base_error_message)
        }
    }

    private companion object {
        const val TAG = "ActiveRequestsForDriverRepository"
    }
}