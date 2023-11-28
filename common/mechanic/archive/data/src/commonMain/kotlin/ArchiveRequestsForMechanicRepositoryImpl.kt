import io.github.aakira.napier.log
import ktor.KtorMechanicArchiveRemoteDataSource
import models.ArchiveRequestsForMechanicItem
import org.company.rado.core.MainRes
import settings.SettingsAuthDataSource

internal class ArchiveRequestsForMechanicRepositoryImpl(
    private val localDataSource: SettingsAuthDataSource,
    private val remoteDataSource: KtorMechanicArchiveRemoteDataSource
) : ArchiveRequestsForMechanicRepository {
    override suspend fun getArchiveRequests(): ArchiveRequestsForMechanicItem {
        val archiveRequestsForMechanicItem = try {
            val userInfo = localDataSource.fetchLoginUserInfo()
            val response = remoteDataSource.fetchArchiveRequests(mechanicId = userInfo.userId)
            log(tag = TAG) { "archive requests for mechanic: $response" }
            ArchiveRequestsForMechanicItem.Success(items = response)
        } catch (e: Exception) {
            log(tag = TAG) { "Error occurred for get archive requests for mechanic" }
            ArchiveRequestsForMechanicItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }
        return archiveRequestsForMechanicItem
    }

    private companion object {
        const val TAG = "ArchiveRequestsForMechanicRepository"
    }
}