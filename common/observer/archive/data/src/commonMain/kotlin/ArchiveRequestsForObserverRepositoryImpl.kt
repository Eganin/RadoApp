import io.github.aakira.napier.log
import ktor.KtorObserverArchiveRemoteDataSource
import models.ArchiveRequestsForObserverItem
import org.company.rado.core.MainRes

internal class ArchiveRequestsForObserverRepositoryImpl(
    private val remoteDataSource: KtorObserverArchiveRemoteDataSource
): ArchiveRequestsForObserverRepository {

    override suspend fun getArchiveRequests(): ArchiveRequestsForObserverItem {
        val archiveRequestsForObserverItem = try{
            val response = remoteDataSource.fetchArchiveRequests()
            log(tag= TAG) { "archive requests for observer: $response" }
            ArchiveRequestsForObserverItem.Success(items = response)
        }catch (e: Exception){
            log(tag= TAG) { "error occurred for get archive requests for observer" }
            ArchiveRequestsForObserverItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }

        return archiveRequestsForObserverItem
    }

    private companion object{
        const val TAG = "ArchiveRequestsForObserverRepository"
    }
}