import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import theme.Theme

object DriverMainScreen : Screen {

    @Composable
    override fun Content() {
        Text(text = "Main Screen for driver", color = Theme.colors.primaryTextColor)
    }
}