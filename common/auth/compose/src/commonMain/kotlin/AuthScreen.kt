import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import dev.icerock.moko.mvvm.compose.viewModelFactory
import models.AuthAction
import other.observeAsState

object AuthScreen : Screen {

    val viewModel = viewModelFactory { AuthViewModel() }.createViewModel()

    @Composable
    override fun Content() {
        val state = viewModel.viewStates().observeAsState()
        val action = viewModel.viewActions().observeAsState()

        AuthView(state = state.value) { event ->
            viewModel.obtainEvent(viewEvent = event)
        }

        when (action.value) {
            is AuthAction.OpenMainFlow -> {}
            is AuthAction.ShowErrorSnackBar -> {}
            null -> {}
        }
    }

}