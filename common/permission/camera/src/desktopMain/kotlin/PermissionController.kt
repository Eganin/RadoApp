actual interface PermissionController {

    actual suspend fun providePermission(permission: Permission)


    actual suspend fun isPermissionGranted(permission: Permission): Boolean


    actual suspend fun getPermissionState(permission: Permission): PermissionState


    companion object {
        fun invoke(): PermissionController = PermissionControllerImpl()
    }

}