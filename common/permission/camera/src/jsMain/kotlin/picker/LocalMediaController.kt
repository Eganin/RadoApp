package picker

import PermissionController
import data.AppBitmap
import data.FileMedia
import data.Media

actual interface LocalMediaController {
    actual val permissionsController: PermissionController
    actual suspend fun pickImage(source: MediaSource): AppBitmap
    actual suspend fun pickImage(
        source: MediaSource,
        maxWidth: Int,
        maxHeight: Int
    ): AppBitmap

    actual suspend fun pickMedia(): Media
    actual suspend fun pickFiles(): FileMedia
    actual suspend fun pickVideo():Media

    companion object {
        fun invoke(): LocalMediaController = LocalMediaControllerImpl()
    }

}

class LocalMediaControllerImpl : LocalMediaController {
    override val permissionsController: PermissionController
        get() = PermissionController.invoke()

    override suspend fun pickImage(source: MediaSource): AppBitmap {
        TODO("Not yet implemented")
    }

    override suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AppBitmap {
        TODO("Not yet implemented")
    }

    override suspend fun pickMedia(): Media {
        TODO("Not yet implemented")
    }

    override suspend fun pickFiles(): FileMedia {
        TODO("Not yet implemented")
    }

    override suspend fun pickVideo(): Media {
        TODO("Not yet implemented")
    }

}