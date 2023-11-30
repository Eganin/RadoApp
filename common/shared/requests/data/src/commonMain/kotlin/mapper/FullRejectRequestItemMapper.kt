package mapper

import ktor.BASE_URL
import models.FullRejectRequestItem
import models.FullRejectRequestResponse
import org.company.rado.core.MainRes
import other.Mapper

class FullRejectRequestItemMapper : Mapper<FullRejectRequestResponse, FullRejectRequestItem> {
    override fun map(source: FullRejectRequestResponse): FullRejectRequestItem {
        return try {
            val rejectRequestInfo = source.copy(images = source.images.map {
                "$BASE_URL/images/" + it.split("/").last()
            },
                videos = source.videos.map {
                    "$BASE_URL/videos/" + it.split("/").last()
                })
            FullRejectRequestItem.Success(request = rejectRequestInfo)
        } catch (e: Exception) {
            FullRejectRequestItem.Error(message = MainRes.string.active_request_info_failure)
        }
    }
}