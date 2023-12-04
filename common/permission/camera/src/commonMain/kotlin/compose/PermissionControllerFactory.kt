package compose

import PermissionController
import androidx.compose.runtime.Composable

fun interface PermissionControllerFactory{
    fun createPermissionController(): PermissionController
}

@Composable
expect fun rememberPermissionControllerFactory(): PermissionControllerFactory