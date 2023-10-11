import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

object ActiveRequestsForDriverScreen : Screen {

    @Composable
    override fun Content() {
        Scaffold {
            ActiveRequestsForDriverView()
        }
    }
}