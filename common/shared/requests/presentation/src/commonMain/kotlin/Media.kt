import data.AppBitmap
import data.Media
import data.MediaType

sealed class MediaTypePresentation {
    data class BitmapType(val bitmap: AppBitmap) : MediaTypePresentation()
    data class PathType(val media: AppMedia) : MediaTypePresentation()
}

data class AppMedia(
    val name: String,
    val path: String,
    val preview: AppBitmap?,
    val type: MediaType
)

fun Media.toAppMedia() = AppMedia(
    name = name, path = path, preview = preview, type = type
)