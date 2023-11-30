import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.viewModelFactory
import other.Position
import other.observeAsState
import views.ArchiveRequestsView

object ArchiveRequestsScreenForObserver : Screen {

    private val position = Position.OBSERVER

    private val viewModel =
        viewModelFactory { ArchiveViewModel(position = position) }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()

        Scaffold(modifier = Modifier.fillMaxSize()) {
            ArchiveRequestsView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp),
                position = position
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }
    }
}

object ArchiveRequestsScreenForDriver : Screen {

    private val position = Position.DRIVER

    private val viewModel =
        viewModelFactory { ArchiveViewModel(position = position) }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()

        Scaffold(modifier = Modifier.fillMaxSize()) {
            ArchiveRequestsView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp),
                position = position
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }
    }
}

object ArchiveRequestsScreenForMechanic : Screen {

    private val position = Position.MECHANIC

    private val viewModel =
        viewModelFactory { ArchiveViewModel(position = position) }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()

        Scaffold(modifier = Modifier.fillMaxSize()) {
            ArchiveRequestsView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp),
                position = position
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }
    }
}