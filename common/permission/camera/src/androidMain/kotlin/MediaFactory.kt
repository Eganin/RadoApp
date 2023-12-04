import android.content.ContentResolver
import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import data.AppBitmap
import data.BitmapUtils.getBitmapOrientation
import data.BitmapUtils.getNormalizedBitmap
import data.Media
import data.MediaType
import java.io.IOException

object MediaFactory {

    fun create(context: Context, uriString: String, type: MediaType): Media {
        val contentResolver = context.contentResolver
        val uri: Uri = Uri.parse(uriString)
        return when (type) {
            MediaType.PHOTO -> createPhotoMedia(contentResolver, uri)
            MediaType.VIDEO -> createVideoMedia(contentResolver, uri)
        }
    }

    @Suppress("ThrowsCount")
    fun create(context: Context, uri: Uri): Media {
        val projection = arrayOf(
            MediaStore.MediaColumns.MIME_TYPE
        )

        val contentResolver = context.contentResolver
        val cursorRef = contentResolver
            .query(uri, projection, null, null, null)
            ?: throw IllegalArgumentException("can't open cursor")

        return cursorRef.use { cursor ->
            if (!cursor.moveToFirst()) {
                error("cursor should have one element")
            }

            val mimeTypeIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
            val mimeType = cursor.getString(mimeTypeIndex)

            when {
                mimeType.startsWith("image") -> createPhotoMedia(contentResolver, uri)
                mimeType.startsWith("video") -> createVideoMedia(contentResolver, uri)
                else -> throw IllegalArgumentException("unsupported type $mimeType")
            }
        }
    }

    @Suppress("ThrowsCount")
    private fun createPhotoMedia(
        contentResolver: ContentResolver,
        uri: Uri
    ): Media {
        val projection = arrayOf(
            MediaStore.Images.ImageColumns.ORIENTATION,
            MediaStore.Images.ImageColumns.TITLE
        )

        val cursorRef = contentResolver
            .query(uri, projection, null, null, null)
            ?: throw IllegalArgumentException("can't open cursor")

        return cursorRef.use { cursor ->
            if (!cursor.moveToFirst()) {
                error("not found resource")
            }

            val orientation = contentResolver.openInputStream(uri)?.use {
                getBitmapOrientation(it)
            } ?: ExifInterface.ORIENTATION_UNDEFINED

            val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.TITLE)
            val title = cursor.getString(titleIndex) ?: uri.lastPathSegment

            val normalizedBitmap = contentResolver.openInputStream(uri)?.use {
                getNormalizedBitmap(it, orientation, sampleSize = null)
            } ?: throw IOException("can't open stream")

            Media(
                name = title.orEmpty(),
                path = uri.toString(),
                type = MediaType.PHOTO,
                preview = AppBitmap(normalizedBitmap)
            )
        }
    }

    @Suppress("ThrowsCount")
    private fun createVideoMedia(
        contentResolver: ContentResolver,
        uri: Uri
    ): Media {
        val projection = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.TITLE
        )

        val cursorRef = contentResolver
            .query(uri, projection, null, null, null)
            ?: throw IllegalArgumentException("can't open cursor")

        return cursorRef.use { cursor ->
            if (!cursor.moveToFirst()) {
                error("cursor should have one element")
            }

            val titleColumn = cursor.getColumnIndex(MediaStore.Video.VideoColumns.TITLE)
            val title = if (titleColumn != -1) {
                cursor.getString(titleColumn)
            } else {
                null
            } ?: uri.lastPathSegment ?: "file"

            val idColumn = cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)
            val thumbnail: android.graphics.Bitmap = if (idColumn != -1) {
                val id = cursor.getLong(idColumn)
                MediaStore.Video.Thumbnails.getThumbnail(
                    contentResolver,
                    id,
                    MediaStore.Video.Thumbnails.MINI_KIND,
                    null
                )
            } else {
                null
            } ?: contentResolver.openFileDescriptor(uri, "r")?.use {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(it.fileDescriptor)
                retriever.getFrameAtTime(0)
            } ?: throw IOException("can't read thumbnail")

            Media(
                name = title,
                path = uri.toString(),
                type = MediaType.VIDEO,
                preview = AppBitmap(thumbnail)
            )
        }
    }
}