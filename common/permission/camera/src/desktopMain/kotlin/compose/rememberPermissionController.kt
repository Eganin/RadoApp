package compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberPermissionControllerFactory(): PermissionControllerFactory {
    return remember {
        PermissionControllerFactory {
            PermissionController.invoke()
        }
    }
}