package manager

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import data.Image
import data.ImageGallery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileNotFoundException

class GalleryPermissionManager : ImageGallery {
    override suspend fun getImages(): List<Image> = withContext(Dispatchers.IO)
    {
        val images = mutableListOf<Image>()
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.SIZE
        )
        val selection = "${MediaStore.Images.Media.SIZE} > 0"
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor =
            appContext.contentResolver.query(queryUri, projection, selection, null, sortOrder)
        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getLong(it.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                val displayName =
                    it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                val data = it.getString(it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val image = Image(Uri.parse(data).toString(), displayName, id.toString())
                images.add(image)
            }
        }
        images
    }

    private fun loadBytesFromUri(uri: Uri): ByteArray {
        val inputStream = appContext.applicationContext.contentResolver.openInputStream(uri)
        inputStream?.use {
            return it.readBytes()
        }
        throw FileNotFoundException("Could not read bytes from Uri: $uri")
    }

    companion object {
        private lateinit var appContext: Context

        fun init(context: Context): GalleryPermissionManager {
            appContext = context
            return GalleryPermissionManager()
        }
    }

}