package ios

import PermissionDelegate
import PermissionState

internal class AlwaysGrantedPermissionDelegate : PermissionDelegate {
    override suspend fun providePermission() = Unit

    override suspend fun getPermissionState(): PermissionState = PermissionState.Granted
}