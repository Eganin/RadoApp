import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.ComposeUIViewController
import ios.PermissionControllerImpl
import navigation.App
import picker.ios.MediaPickerController
import platform.Platform
import platform.PlatformConfiguration
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        //Required for WindowInsets behaviour.
        //Analog of Android Manifest activity.windowSoftInputMode="adjustNothing"
        onFocusBehavior = OnFocusBehavior.DoNothing
    }
) {
    val size = remember { mutableStateOf(IntSize.Zero) }
    val window = UIApplication.sharedApplication.keyWindow
    val viewController = window?.rootViewController
    val permissionsController by remember {
        mutableStateOf(
            MediaPickerController(
                PermissionControllerImpl()
            )
        )
    }

    LaunchedEffect(Unit) {
        if (viewController != null) {
            permissionsController.bind(viewController = viewController)
        }
    }

    PlatformSDK.init(
        platformConfiguration = PlatformConfiguration()
    )

    Box(Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        size.value = coordinates.size
    }) {
        App(platform = Platform.Ios, localMediaController = permissionsController)
    }
}