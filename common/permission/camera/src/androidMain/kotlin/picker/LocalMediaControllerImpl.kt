package picker

import Permission
import PermissionController
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import data.AppBitmap
import data.FileMedia
import data.Media
import kotlin.coroutines.suspendCoroutine

internal class LocalMediaControllerImpl(
    override val permissionsController: PermissionController,
    context: Activity
) : LocalMediaController {
    private val imagePicker = ImagePicker(context)
    private val mediaPicker = MediaPicker(context)
    private var pickerType = PickerType.MEDIA

    override suspend fun pickImage(source: MediaSource): AppBitmap {
        pickerType = PickerType.IMAGE
        return pickImage(source, DEFAULT_MAX_IMAGE_WIDTH, DEFAULT_MAX_IMAGE_HEIGHT)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (pickerType) {
            PickerType.MEDIA -> mediaPicker.onActivityResult(requestCode, resultCode, data)
            PickerType.IMAGE -> imagePicker.onActivityResult(requestCode, resultCode, data)
        }
    }


    override suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AppBitmap {
        source.requiredPermissions().forEach { permission ->
            permissionsController.providePermission(permission)
        }

        val bitmap = suspendCoroutine { continuation ->
            val action: (Result<Bitmap>) -> Unit = { continuation.resumeWith(it) }
            when (source) {
                MediaSource.GALLERY -> imagePicker.pickGalleryImage(action)
                MediaSource.CAMERA -> imagePicker.pickCameraImage(action)
            }
        }

        return AppBitmap(bitmap)
    }

    override suspend fun pickMedia(): Media {
        pickerType = PickerType.MEDIA
        permissionsController.providePermission(Permission.GALLERY)


        return suspendCoroutine { continuation ->
            val action: (Result<Media>) -> Unit = { continuation.resumeWith(it) }
            mediaPicker.pickMedia(action)
        }
    }

    override suspend fun pickFiles(): FileMedia {

        return FileMedia(name = "", path = "")
    }

    private fun MediaSource.requiredPermissions(): List<Permission> {
        return when (this) {
            MediaSource.GALLERY -> listOf(Permission.GALLERY)
            MediaSource.CAMERA -> listOf(Permission.CAMERA)
        }
    }

    enum class PickerType {
        IMAGE,
        MEDIA
    }
}