package mapper

import ktor.BASE_URL
import models.FullRequestItem
import models.FullRequestResponse
import org.company.rado.core.MainRes
import other.Mapper

class FullRequestItemMapper : Mapper<FullRequestResponse,FullRequestItem>{
    override fun map(source: FullRequestResponse): FullRequestItem {
        return try {
            val activeRequestInfo = source.copy(images = source.images.map {
                "$BASE_URL/images/" + it.split("/").last()
            })
            FullRequestItem.Success(request = activeRequestInfo)
        } catch (e: Exception) {
            FullRequestItem.Error(message = MainRes.string.active_request_info_failure)
        }
    }
}