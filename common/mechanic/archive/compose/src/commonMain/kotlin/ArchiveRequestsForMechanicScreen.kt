import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.viewModelFactory
import other.observeAsState
import views.ArchiveRequestsForMechanicView

object ArchiveRequestsForMechanicScreen : Screen {

    private val viewModel = viewModelFactory { MechanicArchiveViewModel() }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()

        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) {
            ArchiveRequestsForMechanicView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp)
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }
    }
}