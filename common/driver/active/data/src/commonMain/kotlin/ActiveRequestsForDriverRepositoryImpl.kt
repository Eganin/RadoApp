import io.github.aakira.napier.log
import ktor.KtorDriverActiveRemoteDataSource
import ktor.models.KtorActiveRequest
import ktor.models.KtorCreateRequest
import models.ActiveRequestsForDriverItem
import models.CreateRequestIdItem
import models.CreateRequestIdResponse
import org.company.rado.core.MainRes
import other.Mapper
import settings.SettingsAuthDataSource

class ActiveRequestsForDriverRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorDriverActiveRemoteDataSource,
    private val createRequestMapper: Mapper<CreateRequestIdResponse, CreateRequestIdItem>
) : ActiveRequestsForDriverRepository {
    override suspend fun createRequest(
        typeVehicle: String,
        numberVehicle: String,
        faultDescription: String
    ): CreateRequestIdItem {
        val createRequestItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.createRequest(
                request = KtorCreateRequest(
                    driverUsername = userInfo.fullName,
                    typeVehicle = typeVehicle,
                    numberVehicle = numberVehicle,
                    faultDescription = faultDescription
                )
            )
            createRequestMapper.map(source = response)
        } catch (e: Exception) {
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
            ActiveRequestsForDriverItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }
        return activeRequestsForDriverItem
    }

    override suspend fun createResourcesImages(image: Pair<String, ByteArray>) {
        try {
            remoteDataSource.uploadResourceImage(image = image)
        } catch (e: Exception) {
            e.printStackTrace()
            log(tag = TAG) { e.printStackTrace().toString() }
        }
    }

    override suspend fun createImagesForRequest(
        requestId: Int,
        images: List<Pair<String, ByteArray>>
    ) {
        try {
            images.forEach {image->
                remoteDataSource.uploadImagesForRequest(requestId = requestId, image = image)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            log(tag = TAG) { e.printStackTrace().toString() }
        }
    }

    private companion object {
        const val TAG = "ActiveRequestsForDriverRepository"
    }
}