import androidx.compose.ui.window.Window
import cafe.adriel.voyager.navigator.Navigator
import navigation.SplashScreen
import org.jetbrains.skiko.wasm.onWasmReady
import platform.Platform
import platform.PlatformConfiguration
import theme.AppTheme

fun main() {
    onWasmReady {
        Window("Rado") {
            PlatformSDK.init(
                platformConfiguration= PlatformConfiguration()
            )
            AppTheme{
                Navigator(SplashScreen(platform = Platform.Web))
            }
        }
    }
}
