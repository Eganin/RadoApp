package compose

import PermissionController
import androidx.compose.runtime.Composable

fun interface PermissionControllerFactory{
    fun createPermissionController(): PermissionController
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
@Composable
expect fun rememberPermissionControllerFactory(): PermissionControllerFactory