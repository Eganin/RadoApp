import io.github.aakira.napier.log
import ktor.KtorDriverArchiveRemoteDataSource
import models.ArchiveRequestsForDriverItem
import org.company.rado.core.MainRes
import settings.SettingsAuthDataSource

internal class ArchiveRequestsForDriverRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorDriverArchiveRemoteDataSource
) : ArchiveRequestsForDriverRepository {
    override suspend fun getArchiveRequests(): ArchiveRequestsForDriverItem {
        val archiveRequestsForDriverItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.fetchArchiveRequests(driverId = userInfo.userId)
            log(tag = TAG) { "archive requests for driver: $response" }
            ArchiveRequestsForDriverItem.Success(items = response)
        } catch (e: Exception) {
            log(tag = TAG) { "Error occurred for get archive requests for driver" }
            ArchiveRequestsForDriverItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }

        return archiveRequestsForDriverItem
    }

    private companion object {
        const val TAG = "ArchiveRequestsForDriverRepository"
    }
}