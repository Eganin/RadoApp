import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import java.lang.ref.WeakReference

internal class PermissionResolver(val context: Context){
    private var activity : WeakReference<ComponentActivity>? = null

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

    private val permissionCallbackMap = mutableMapOf<Int,PermissionCallback>()

    fun requestPermission(
        permission: Permission,
        permissions: List<String>,
        callback: (Result<Boolean>) -> Unit
    ) {
        focusedActivity?.lifecycleScope?.launchWhenCreated {
            val toRequest = permissions.filter {
                ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
            }

            if (toRequest.isEmpty()) {
                callback.invoke(Result.success(true))
                return@launchWhenCreated
            }

            val requestCode = (permissionCallbackMap.keys.maxOrNull() ?: 0) + 1
            permissionCallbackMap[requestCode] = PermissionCallback(permission, callback)
            focusedActivity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    toRequest.toTypedArray(),
                    requestCode
                )
            }
        }
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        val permissionCallback = permissionCallbackMap[requestCode] ?: return
        permissionCallbackMap.remove(requestCode)
        managePermissions(permissionCallback, permissions, grantResults)
    }

    private fun managePermissions(
        permissionCallback: PermissionCallback,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        val isCancelled = grantResults.isEmpty() || permissions.isEmpty()
        if (isCancelled) {
            permissionCallback.callback.invoke(
                Result.failure(RequestCancelledException(permissionCallback.permission))
            )
            return
        }
        val success = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
        if (success) {
            permissionCallback.callback.invoke(Result.success(true))
        } else {
            if (focusedActivity?.let {
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        it,
                        permissions.first() ?: ""
                    )
                } == true) {
                permissionCallback.callback.invoke(
                    Result.failure(DeniedException(permissionCallback.permission))
                )
            } else {
                permissionCallback.callback.invoke(
                    Result.failure(DeniedAlwaysException(permissionCallback.permission))
                )
            }
        }
    }

    fun shouldShowRequestPermissionRationale(it: String) =
        focusedActivity?.shouldShowRequestPermissionRationale(it) ?: false

    private class PermissionCallback(
        val permission: Permission,
        val callback: (Result<Boolean>) -> Unit
    )
}