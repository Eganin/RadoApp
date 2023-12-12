import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import data.ImageGallery
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import manager.GalleryPermissionManager
import kotlin.coroutines.suspendCoroutine

class PermissionControllerImpl(
    private val applicationContext: Context
) : PermissionController {
    private val mutex: Mutex = Mutex()
    private val permissionResolver = PermissionResolver(applicationContext)
    private val gallery: ImageGallery = GalleryPermissionManager.init(context = applicationContext)
    override suspend fun providePermission(permission: Permission) {
        mutex.withLock {
            val platformPermission = permission.toPlatformPermission()
            val result = suspendCoroutine { continuation ->
                permissionResolver.requestPermission(
                    permission,
                    platformPermission
                ) { continuation.resumeWith(it) }
            }
            /*result.takeIf { it }?.let {
                processResult(permission)
            }*/
        }
    }

    private fun processResult(permission: Permission) = when (permission) {
        Permission.CAMERA -> gallery
        Permission.GALLERY -> gallery
        Permission.STORAGE -> gallery
        Permission.WRITE_STORAGE -> gallery
        Permission.REMOTE_NOTIFICATION -> gallery
    }

    override suspend fun isPermissionGranted(permission: Permission): Boolean {
        return getPermissionState(permission) == PermissionState.Granted
    }

    @Suppress("ReturnCount")
    override suspend fun getPermissionState(permission: Permission): PermissionState {
        if (permission == Permission.REMOTE_NOTIFICATION &&
            Build.VERSION.SDK_INT in VERSIONS_WITHOUT_NOTIFICATION_PERMISSION
        ) {
            val isNotificationsEnabled = NotificationManagerCompat.from(applicationContext)
                .areNotificationsEnabled()
            return if (isNotificationsEnabled) {
                PermissionState.Granted
            } else {
                PermissionState.DeniedAlways
            }
        }
        val permissions: List<String> = permission.toPlatformPermission()
        val status: List<Int> = permissions.map {
            ContextCompat.checkSelfPermission(applicationContext, it)
        }
        val isAllGranted: Boolean = status.all { it == PackageManager.PERMISSION_GRANTED }
        if (isAllGranted) return PermissionState.Granted
        val isAllRequestRationale: Boolean = permissions.all {
            !permissionResolver.shouldShowRequestPermissionRationale(it)
        }
        return if (isAllRequestRationale) PermissionState.NotDetermined
        else PermissionState.Denied
    }

    override fun onDataReceived(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        permissionResolver.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun openAppSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", applicationContext.packageName, null)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        applicationContext.startActivity(intent)
    }


    private fun Permission.toPlatformPermission(): List<String> {
        return when (this) {
            Permission.CAMERA -> listOf(Manifest.permission.CAMERA)
            Permission.GALLERY -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                listOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
            } else {
                listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            }

            Permission.STORAGE -> listOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            Permission.WRITE_STORAGE -> listOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            Permission.REMOTE_NOTIFICATION -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    listOf(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    emptyList()
                }
            }
        }
    }

    private companion object {
        val VERSIONS_WITHOUT_NOTIFICATION_PERMISSION =
            Build.VERSION_CODES.KITKAT until Build.VERSION_CODES.TIRAMISU
    }
}