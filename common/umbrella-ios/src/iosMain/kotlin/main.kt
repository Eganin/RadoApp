import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import navigation.App
import platform.Platform
import platform.PlatformConfiguration
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        //Required for WindowInsets behaviour.
        //Analog of Android Manifest activity.windowSoftInputMode="adjustNothing"
        onFocusBehavior = OnFocusBehavior.DoNothing
    }
) {
    PlatformSDK.init(
        platformConfiguration = PlatformConfiguration()
    )
    App(platform = Platform.Ios)
}