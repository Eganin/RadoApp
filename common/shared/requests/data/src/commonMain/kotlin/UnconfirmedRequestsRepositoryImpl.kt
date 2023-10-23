import ktor.KtorSharedRequestsRemoteDataSource
import models.FullUnconfirmedRequestResponse
import models.UnconfirmedRequestInfoItem
import org.company.rado.core.MainRes
import other.Mapper

class UnconfirmedRequestsRepositoryImpl(
    private val remoteDataSource: KtorSharedRequestsRemoteDataSource,
    private val unconfirmedRequestInfoItemMapper: Mapper<FullUnconfirmedRequestResponse, UnconfirmedRequestInfoItem>
) : UnconfirmedRequestsRepository {
    override suspend fun getInfoForUnconfirmedRequest(requestId: Int): UnconfirmedRequestInfoItem {
        val unconfirmedRequestInfoItem = try {
            unconfirmedRequestInfoItemMapper.map(
                source = remoteDataSource.fetchUnconfirmedRequestInfo(requestId = requestId)
            )
        } catch (e: Exception) {
            UnconfirmedRequestInfoItem.Error(message = MainRes.string.unconfirmed_request_info_is_not_fetch)
        }

        return unconfirmedRequestInfoItem
    }
}