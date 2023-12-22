package picker.ios

import Permission
import PermissionController
import data.AppBitmap
import data.Media
import kotlinx.cinterop.ExperimentalForeignApi
import picker.AdaptivePresentationDelegateToContinuation
import picker.DEFAULT_MAX_IMAGE_HEIGHT
import picker.DEFAULT_MAX_IMAGE_WIDTH
import picker.ImagePickerDelegateToContinuation
import picker.MediaSource
import platform.CoreServices.kUTTypeImage
import platform.CoreServices.kUTTypeMovie
import platform.CoreServices.kUTTypeVideo
import platform.Foundation.CFBridgingRelease
import platform.UIKit.UIImagePickerController
import platform.UIKit.UIImagePickerControllerCameraCaptureMode
import platform.UIKit.UIImagePickerControllerSourceType
import platform.UIKit.UIViewController
import platform.UIKit.presentationController
import kotlin.coroutines.suspendCoroutine
import kotlin.native.ref.WeakReference

class MediaPickerController(
    override val permissionsController: PermissionController
) : MediaPickerControllerProtocol {
    private val strongRefs: MutableSet<Any> = mutableSetOf()
    private lateinit var getViewController: () -> UIViewController

    override fun bind(viewController: UIViewController) {
        val weakRef: WeakReference<UIViewController> = WeakReference(viewController)
        this.getViewController = { weakRef.get() ?: error("viewController was deallocated") }
    }

    override suspend fun pickImage(source: MediaSource): AppBitmap {
        return pickImage(source, DEFAULT_MAX_IMAGE_WIDTH, DEFAULT_MAX_IMAGE_HEIGHT)
    }

    override suspend fun pickImage(source: MediaSource, maxWidth: Int, maxHeight: Int): AppBitmap {
        source.requiredPermissions().forEach { permission ->
            permissionsController.providePermission(permission)
        }

        val refs: MutableSet<Any> = mutableSetOf()
        strongRefs.add(refs)
        val media: Media = suspendCoroutine { continuation ->
            val controller = UIImagePickerController()
            controller.sourceType = source.toSourceType()
            controller.mediaTypes = listOf(kImageType)
            controller.delegate = ImagePickerDelegateToContinuation(continuation).also {
                refs.add(it)
            }
            getViewController().presentViewController(
                controller,
                animated = true,
                completion = null
            )
            controller.presentationController?.delegate =
                AdaptivePresentationDelegateToContinuation(continuation).also {
                    refs.add(it)
                }
        }
        strongRefs.remove(refs)
        return media.preview
    }

    override suspend fun pickVideo(): Media {
        permissionsController.providePermission(Permission.CAMERA)

        val refs: MutableSet<Any> = mutableSetOf()
        strongRefs.add(refs)
        val media: Media = suspendCoroutine { continuation ->
            val controller = UIImagePickerController()
            controller.sourceType =
                UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
            controller.mediaTypes = listOf(kMovieType)
            controller.cameraCaptureMode =
                UIImagePickerControllerCameraCaptureMode.UIImagePickerControllerCameraCaptureModeVideo
            controller.delegate = ImagePickerDelegateToContinuation(continuation).also {
                refs.add(it)
            }
            getViewController().presentViewController(
                controller,
                animated = true,
                completion = null
            )
            controller.presentationController?.delegate =
                AdaptivePresentationDelegateToContinuation(continuation).also {
                    refs.add(it)
                }
        }
        strongRefs.remove(refs)
        return media

    }

    private fun MediaSource.requiredPermissions(): List<Permission> =
        when (this) {
            MediaSource.CAMERA -> listOf(Permission.CAMERA)
        }

    private fun MediaSource.toSourceType(): UIImagePickerControllerSourceType =
        when (this) {
            MediaSource.CAMERA -> UIImagePickerControllerSourceType.UIImagePickerControllerSourceTypeCamera
        }

    @OptIn(ExperimentalForeignApi::class)
    internal companion object {
        val kVideoType = CFBridgingRelease(kUTTypeVideo) as String
        val kMovieType = CFBridgingRelease(kUTTypeMovie) as String
        val kImageType = CFBridgingRelease(kUTTypeImage) as String
    }
}