import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import navigation.App
import platform.Platform
import platform.PlatformConfiguration
import java.awt.Dimension

fun main() = application {
    PlatformSDK.init(
        platformConfiguration = PlatformConfiguration()
    )
    Window(
        title = "Rado",
        state = rememberWindowState(width = 1000.dp, height = 1000.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        App(platform = Platform.Desktop)
    }
}