import androidx.compose.ui.window.Window
import cafe.adriel.voyager.navigator.Navigator
import navigation.HomeScreen
import org.jetbrains.skiko.wasm.onWasmReady
import platform.PlatformConfiguration

fun main() {
    onWasmReady {
        Window("Rado") {
            PlatformSDK.init(
                platformConfiguration= PlatformConfiguration()
            )
            Navigator(HomeScreen)
        }
    }
}
