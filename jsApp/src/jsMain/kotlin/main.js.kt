import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import navigation.App
import org.jetbrains.skiko.wasm.onWasmReady
import platform.Platform
import platform.PlatformConfiguration

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("Rado") {
            PlatformSDK.init(
                platformConfiguration = PlatformConfiguration()
            )
            App(platform = Platform.Web)
        }
    }
}
