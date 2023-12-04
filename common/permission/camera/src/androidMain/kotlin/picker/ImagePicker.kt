package picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import data.BitmapUtils
import data.CanceledException
import io.github.aakira.napier.log
import java.io.File
import java.lang.ref.WeakReference

internal class ImagePicker(private val context: Context) {
    private var activity: WeakReference<ComponentActivity>? = null

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()
    private var photoFilePath: String? = null
    private val maxImageWidth
        get() =
            DEFAULT_MAX_IMAGE_WIDTH
    private val maxImageHeight
        get() =
            DEFAULT_MAX_IMAGE_HEIGHT


    init {
        val activity = context as? ComponentActivity
        this.activity = WeakReference(activity)
    }

    private val focusedActivity: ComponentActivity?
        get() = activity?.get()?.let {
            if (it.isFinishing || it.isDestroyed) null else {
                it
            }
        }


    fun pickGalleryImage(callback: (Result<android.graphics.Bitmap>) -> Unit) {
        val requestCode = codeCallbackMap.keys.sorted().lastOrNull() ?: 0

        codeCallbackMap[requestCode] =
            CallbackData.Gallery(
                callback
            )

        val intent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    fun pickCameraImage(callback: (Result<android.graphics.Bitmap>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0
        log(tag="IMAGE") { "OOOOOOOOO" }

        val outputUri = createPhotoUri()
        codeCallbackMap[requestCode] =
            CallbackData.Camera(
                callback,
                outputUri
            )

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    private fun createPhotoUri(): Uri {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val tmpFile = File(filesDir, DEFAULT_FILE_NAME)
        photoFilePath = tmpFile.absolutePath

        return FileProvider.getUriForFile(
            context,
            context.packageName + FILE_PROVIDER_SUFFIX,
            tmpFile
        )
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val callbackData = codeCallbackMap[requestCode] ?: return
        codeCallbackMap.remove(requestCode)

        val callback = callbackData.callback

        if (resultCode == Activity.RESULT_CANCELED) {
            callback.invoke(Result.failure(CanceledException()))
            return
        }

        when (callbackData) {
            is CallbackData.Gallery -> {
                val uri = data?.data
                if (uri != null) {
                    processResult(callback, uri)
                } else {
                    callback.invoke(Result.failure(IllegalArgumentException(data?.toString())))
                }
            }

            is CallbackData.Camera -> {
                processResult(callback, callbackData.outputUri)
            }
        }
    }

    @Suppress("ReturnCount")
    private fun processResult(
        callback: (Result<android.graphics.Bitmap>) -> Unit,
        uri: Uri
    ) {
        val contentResolver = focusedActivity?.contentResolver

        val bitmapOptions = contentResolver?.openInputStream(uri)?.use {
            BitmapUtils.getBitmapOptionsFromStream(it)
        } ?: run {
            callback.invoke(Result.failure(NoAccessToFileException(uri.toString())))
            return
        }

        val sampleSize =
            BitmapUtils.calculateInSampleSize(bitmapOptions, maxImageWidth, maxImageHeight)

        val orientation = contentResolver.openInputStream(uri)?.use {
            BitmapUtils.getBitmapOrientation(it)
        } ?: run {
            callback.invoke(Result.failure(NoAccessToFileException(uri.toString())))
            return
        }

        val bitmap = contentResolver.openInputStream(uri)?.use {
            BitmapUtils.getNormalizedBitmap(it, orientation, sampleSize)
        } ?: run {
            callback.invoke(Result.failure(NoAccessToFileException(uri.toString())))
            return
        }

        callback.invoke(Result.success(bitmap))
    }

    sealed class CallbackData(val callback: (Result<android.graphics.Bitmap>) -> Unit) {
        class Gallery(callback: (Result<android.graphics.Bitmap>) -> Unit) :
            CallbackData(callback)

        class Camera(
            callback: (Result<android.graphics.Bitmap>) -> Unit,
            val outputUri: Uri
        ) : CallbackData(callback)
    }

    companion object {
        private const val DEFAULT_FILE_NAME = "image.png"
        private const val PHOTO_FILE_PATH_KEY = "photoFilePath"
        private const val FILE_PROVIDER_SUFFIX = ".provider"

        private const val ARG_IMG_MAX_WIDTH = "args_img_max_width"
        private const val ARG_IMG_MAX_HEIGHT = "args_img_max_height"
    }
}