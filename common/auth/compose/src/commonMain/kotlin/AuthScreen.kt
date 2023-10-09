import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen

object AuthScreen : Screen {
    @Composable
    override fun Content() {
        Text(text = "Auth Screen", color = Color.Black)
    }

}