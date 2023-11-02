package mapper

import ktor.BASE_URL
import models.FullUnconfirmedRequestResponse
import models.UnconfirmedRequestInfoItem
import org.company.rado.core.MainRes
import other.Mapper

class UnconfirmedRequestInfoItemMapper :
    Mapper<FullUnconfirmedRequestResponse, UnconfirmedRequestInfoItem> {
    override fun map(source: FullUnconfirmedRequestResponse): UnconfirmedRequestInfoItem {
        return try {
            val unconfirmedRequestInfo = source.copy(images = source.images.map {
                "$BASE_URL/images/" + it.split("/").last()
            })
            UnconfirmedRequestInfoItem.Success(requestInfo = unconfirmedRequestInfo)
        } catch (e: Exception) {
            UnconfirmedRequestInfoItem.Error(message = MainRes.string.unconfirmed_request_info_is_not_fetch)
        }
    }
}