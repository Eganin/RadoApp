@Suppress("NO_ACTUAL_FOR_EXPECT")
expect interface PermissionController {

    suspend fun providePermission(permission: Permission)

    suspend fun isPermissionGranted(permission: Permission): Boolean

    suspend fun getPermissionState(permission: Permission): PermissionState

    fun openAppSettings()
}