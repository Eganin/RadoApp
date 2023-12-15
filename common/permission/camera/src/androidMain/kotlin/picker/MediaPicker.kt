package picker

import MediaFactory
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.core.content.FileProvider
import data.CanceledException
import data.Media
import java.io.File
import java.lang.ref.WeakReference

internal class MediaPicker(private val context: Context) {
    private var activity: WeakReference<ComponentActivity>? = null

    private val codeCallbackMap = mutableMapOf<Int, CallbackData>()

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

    fun pickVideo(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData.Default(callback)

        val intent = Intent().apply {
            type = "video/*"
            action = Intent.ACTION_GET_CONTENT
        }

        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    fun pickMedia(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData.Default(callback)

        val intent = Intent().apply {
            type = "image/* video/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*", "image/*"))
        }
        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    fun pickCameraMedia(callback: (Result<Media>) -> Unit){
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        val outputUri = createVideoUri()
        codeCallbackMap[requestCode] = CallbackData.Camera(callback,outputUri)

        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            .putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    private fun createVideoUri(): Uri{
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM)
        val tmpFile = File(filesDir,DEFAULT_FILE_NAME)

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
            is CallbackData.Default -> {
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
        callback: (Result<Media>) -> Unit,
        uri: Uri
    ) {
        val contentResolver = focusedActivity?.contentResolver

        val result = kotlin.runCatching {
            MediaFactory.createVideoMedia(contentResolver!!,uri)
        }
        callback.invoke(result)
    }

    sealed class CallbackData(val callback: (Result<Media>) -> Unit) {
        class Default(callback: (Result<Media>) -> Unit) :
            CallbackData(callback)

        class Camera(
            callback: (Result<Media>) -> Unit,
            val outputUri: Uri
        ) : CallbackData(callback)
    }

    private companion object{
        const val DEFAULT_FILE_NAME = "default.mp4"
        private const val FILE_PROVIDER_SUFFIX = ".provider"
    }
}