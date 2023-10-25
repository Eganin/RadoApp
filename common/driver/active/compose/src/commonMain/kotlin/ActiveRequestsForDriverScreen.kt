import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch
import models.DriverActiveAction
import other.observeAsState
import views.ActiveRequestsForDriverView

object ActiveRequestsForDriverScreen : Screen {

    private val viewModel = viewModelFactory { DriverActiveViewModel() }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()

        val scope = rememberCoroutineScope()
        val snackBarHostState = remember { SnackbarHostState() }

        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState)
            }
        ) {
            ActiveRequestsForDriverView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp)
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }

        when (action.value) {
            is DriverActiveAction.ShowErrorSnackBar -> {
                val snackBarAction = action.value as DriverActiveAction.ShowErrorSnackBar
                scope.launch {
                    snackBarHostState.showSnackbar(message = snackBarAction.message)
                }
            }

            null -> {}
        }
    }
}