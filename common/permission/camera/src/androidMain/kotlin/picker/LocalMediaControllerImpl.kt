package picker

import Permission
import PermissionController
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import data.AppBitmap
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
                MediaSource.CAMERA -> imagePicker.pickCameraImage(action)
            }
        }

        return AppBitmap(bitmap)
    }

    override suspend fun pickVideo(): Media {
        pickerType = PickerType.MEDIA
        permissionsController.providePermission(Permission.CAMERA)

        return suspendCoroutine { continuation ->
            val action: (Result<Media>) -> Unit = { continuation.resumeWith(it) }
            mediaPicker.pickCameraMedia(action)
        }
    }

    private fun MediaSource.requiredPermissions(): List<Permission> {
        return when (this) {
            MediaSource.CAMERA -> listOf(Permission.CAMERA)
        }
    }

    enum class PickerType {
        IMAGE,
        MEDIA
    }
}