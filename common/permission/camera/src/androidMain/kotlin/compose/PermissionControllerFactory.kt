package compose

import PermissionController
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun rememberPermissionControllerFactory(): PermissionControllerFactory {
    val context = LocalContext.current as Activity
    return remember(context) {
        PermissionControllerFactory {
            PermissionController(applicationContext = context)
        }
    }
}