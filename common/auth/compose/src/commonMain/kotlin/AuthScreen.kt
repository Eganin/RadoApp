import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.icerock.moko.mvvm.compose.viewModelFactory
import kotlinx.coroutines.launch
import models.AuthAction
import models.Position
import navigation.MainDriverSharedScreen
import navigation.MainMechanicSharedScreen
import navigation.MainObserverSharedScreen
import other.observeAsState

object AuthScreen : Screen {

    private val viewModel = viewModelFactory { AuthViewModel() }.createViewModel()

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
            AuthView(state = state.value) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
        }

        when (action.value) {
            is AuthAction.OpenMainFlow -> {
                val navigator = LocalNavigator.currentOrThrow
                val authAction = action.value as AuthAction.OpenMainFlow
                when(authAction.position){
                    Position.DRIVER->{
                        val mainDriverScreen = rememberScreen(MainDriverSharedScreen.Main)
                        navigator.push(mainDriverScreen)
                    }
                    Position.MECHANIC->{
                        val mainMechanicScreen = rememberScreen(MainMechanicSharedScreen.Main)
                        navigator.push(mainMechanicScreen)
                    }
                    Position.OBSERVER->{
                        val mainObserverScreen = rememberScreen(MainObserverSharedScreen.Main)
                        navigator.push(mainObserverScreen)
                    }
                }
            }
            is AuthAction.ShowErrorSnackBar -> {
                val snackBarAction = action.value as AuthAction.ShowErrorSnackBar
                scope.launch {
                    snackBarHostState.showSnackbar(message = snackBarAction.message)
                }
            }

            null -> {}
        }
    }

}