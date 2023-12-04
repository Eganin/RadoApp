package compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ios.PermissionControllerImpl

@Composable
actual fun rememberPermissionControllerFactory(): PermissionControllerFactory {
    return remember {
        PermissionControllerFactory {
            PermissionControllerImpl()
        }
    }
}