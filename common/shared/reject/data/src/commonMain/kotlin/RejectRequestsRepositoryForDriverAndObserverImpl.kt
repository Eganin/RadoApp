import io.github.aakira.napier.log
import ktor.KtorSharedRejectRemoteDataSource
import models.SmallRejectRequestItem
import org.company.rado.core.MainRes
import settings.SettingsAuthDataSource

internal class RejectRequestsRepositoryForDriverAndObserverImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorSharedRejectRemoteDataSource
) : RejectRequestsRepositoryForDriverAndObserver {
    override suspend fun getRejectRequestsForObserver(): SmallRejectRequestItem {
        val smallRejectRequestItem = try {
            val items = remoteDataSource.getRejectRequestsForObserver()
            log(tag= TAG) { "Reject requests for observer: $items" }
            SmallRejectRequestItem.Success(items = items)
        } catch (e: Exception) {
            log(tag= TAG) { "Reject requests for observer is failure" }
            SmallRejectRequestItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }
        return smallRejectRequestItem
    }

    override suspend fun getRejectRequestsForDriver(driverId: Int): SmallRejectRequestItem {
        val smallRejectRequestItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val items = remoteDataSource.getRejectRequestsForDriver(driverId = userInfo.userId)
            log(tag= TAG) { "Reject requests for driver: $items" }
            SmallRejectRequestItem.Success(items = items)
        } catch (e: Exception) {
            log(tag= TAG) { "Reject requests for driver is failure" }
            SmallRejectRequestItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }
        return smallRejectRequestItem
    }

    private companion object {
        const val TAG = "RejectRequestsRepositoryForDriverAndObserver"
    }
}