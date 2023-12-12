import android.app.Activity

actual interface PermissionController {

    actual suspend fun providePermission(permission: Permission)
    actual suspend fun isPermissionGranted(permission: Permission): Boolean
    actual suspend fun getPermissionState(permission: Permission): PermissionState

    actual fun openAppSettings()

    fun onDataReceived(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    )

    companion object {
        operator fun invoke(
            applicationContext: Activity
        ): PermissionController {
            return PermissionControllerImpl(
                applicationContext = applicationContext
            )
        }
    }
}