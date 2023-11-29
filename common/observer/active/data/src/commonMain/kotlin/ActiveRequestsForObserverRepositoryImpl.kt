import io.github.aakira.napier.log
import ktor.KtorObserverActiveRemoteDataSource
import ktor.models.KtorActiveRequestForObserver
import models.ActiveRequestsForObserverItem
import org.company.rado.core.MainRes

internal class ActiveRequestsForObserverRepositoryImpl(
    private val remoteDataSource: KtorObserverActiveRemoteDataSource
) : ActiveRequestsForObserverRepository {
    override suspend fun getRequestsByDate(date: String): ActiveRequestsForObserverItem {
        val activeRequestsForMechanicItem = try {
            val response = remoteDataSource.fetchActiveRequestsByDate(
                request = KtorActiveRequestForObserver(date = date)
            )
            ActiveRequestsForObserverItem.Success(items = response)
        } catch (e: Exception) {
            log(tag = TAG) { "Error occurred for get requests by date: $date" }
            ActiveRequestsForObserverItem.Error(message = MainRes.string.requests_by_date_is_not_fetch)
        }

        return activeRequestsForMechanicItem
    }

    private companion object {
        const val TAG = "ActiveRequestsForObserverRepository"
    }
}