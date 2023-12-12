package picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import data.CanceledException
import data.Media
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

        codeCallbackMap[requestCode] = CallbackData(callback)

        val intent = Intent().apply {
            type = "video/*"
            action = Intent.ACTION_GET_CONTENT
        }

        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    fun pickMedia(callback: (Result<Media>) -> Unit) {
        val requestCode = codeCallbackMap.keys.maxOrNull() ?: 0

        codeCallbackMap[requestCode] = CallbackData(callback)

        val intent = Intent().apply {
            type = "image/* video/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("video/*", "image/*"))
        }
        focusedActivity?.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val callbackData = codeCallbackMap[requestCode] ?: return
        codeCallbackMap.remove(requestCode)

        val callback = callbackData.callback

        if (resultCode == Activity.RESULT_CANCELED) {
            callback.invoke(Result.failure(CanceledException()))
            return
        }

        processResult(callback, data)
    }

    @Suppress("ReturnCount")
    private fun processResult(
        callback: (Result<Media>) -> Unit,
        intent: Intent?
    ) {
        val context = this.context
        if (intent == null) {
            callback(Result.failure(IllegalStateException("intent unavailable")))
            return
        }
        val intentData = intent.data
        if (intentData == null) {
            callback(Result.failure(IllegalStateException("intentData unavailable")))
            return
        }

        val result = kotlin.runCatching {
            MediaFactory.create(context, intentData)
        }
        callback.invoke(result)
    }

    class CallbackData(val callback: (Result<Media>) -> Unit)
}