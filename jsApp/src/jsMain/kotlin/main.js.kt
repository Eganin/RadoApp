import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import cafe.adriel.voyager.navigator.Navigator
import navigation.SplashScreen
import org.jetbrains.skiko.wasm.onWasmReady
import platform.Platform
import platform.PlatformConfiguration
import theme.AppTheme

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("Rado") {
            PlatformSDK.init(
                platformConfiguration = PlatformConfiguration()
            )
            AppTheme {
                Navigator(SplashScreen(platform = Platform.Web))
            }
        }
    }
}
