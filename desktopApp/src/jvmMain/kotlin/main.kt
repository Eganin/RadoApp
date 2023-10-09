import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cafe.adriel.voyager.navigator.Navigator
import navigation.SplashScreen
import platform.Platform
import platform.PlatformConfiguration
import theme.AppTheme
import java.awt.Dimension

fun main() = application {
    PlatformSDK.init(
        platformConfiguration = PlatformConfiguration()
    )
    Window(
        title = "Rado",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        window.minimumSize = Dimension(350, 600)
        AppTheme{
            Navigator(SplashScreen(platform=Platform.Desktop))
        }
    }
}