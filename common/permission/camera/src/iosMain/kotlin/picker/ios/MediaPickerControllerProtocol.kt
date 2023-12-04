package picker.ios

import PermissionController
import data.AppBitmap
import data.FileMedia
import data.Media
import picker.MediaSource
import platform.UIKit.UIViewController

interface MediaPickerControllerProtocol {
    val permissionsController: PermissionController

    fun bind(viewController: UIViewController)

    suspend fun pickImage(source: MediaSource): AppBitmap
    suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AppBitmap
    suspend fun pickMedia(): Media
    suspend fun pickFiles(): FileMedia
}