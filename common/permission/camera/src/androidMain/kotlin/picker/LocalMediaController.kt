package picker

import PermissionController
import android.app.Activity
import android.content.Intent
import data.AppBitmap
import data.FileMedia
import data.Media

actual interface LocalMediaController {
    actual val permissionsController: PermissionController

    actual suspend fun pickImage(source: MediaSource): AppBitmap
    actual suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AppBitmap
    actual suspend fun pickMedia(): Media
    actual suspend fun pickFiles(): FileMedia
    actual suspend fun pickVideo():Media
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    companion object {
        operator fun invoke(
            context: Activity
        ): LocalMediaController {
            return LocalMediaControllerImpl(
                permissionsController = PermissionController(context),
                context = context
            )
        }
    }
}