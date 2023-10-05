import androidx.compose.ui.window.Window
import org.jetbrains.skiko.wasm.onWasmReady
import platform.PlatformConfiguration

fun main() {
    onWasmReady {
        Window("Rado") {
            PlatformSDK.init(
                platformConfiguration= PlatformConfiguration()
            )
            TestGreeting()
        }
    }
}
