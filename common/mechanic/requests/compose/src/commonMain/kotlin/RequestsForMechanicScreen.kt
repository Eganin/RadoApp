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
import models.MechanicRequestsAction
import models.MechanicRequestsEvent
import org.company.rado.core.MainRes
import other.observeAsState
import views.MechanicRejectDialog
import views.RequestsMechanicView
import views.create.FailureRequestDialog
import views.create.SuccessRequestDialog

object RequestsForMechanicScreen : Screen {

    private val viewModel = viewModelFactory { MechanicRequestsViewModel() }.createViewModel()

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
            RequestsMechanicView(
                state = state.value,
                modifier = Modifier.padding(bottom = 90.dp)
            ) { event ->
                viewModel.obtainEvent(viewEvent = event)
            }
            if (state.value.showRejectDialog) {
                MechanicRejectDialog(
                    mechanicComment = state.value.mechanicComment,
                    onSend = {
                        viewModel.obtainEvent(MechanicRequestsEvent.SendRejectRequest)
                    },
                    onExit = {
                        viewModel.obtainEvent(MechanicRequestsEvent.CloseMechanicRejectDialog)
                    },
                    onValueChange = {
                        viewModel.obtainEvent(
                            MechanicRequestsEvent.CommentMechanicValueChange(
                                commentMechanic = it
                            )
                        )
                    })
            }

            if (state.value.showSuccessRejectDialog) {
                SuccessRequestDialog(
                    onDismiss = {
                        viewModel.obtainEvent(MechanicRequestsEvent.CloseMechanicRejectDialogWithSuccess)
                    }, onExit = {
                        viewModel.obtainEvent(MechanicRequestsEvent.CloseMechanicRejectDialogWithSuccess)
                    },
                    firstText = MainRes.string.reject_request_title_dialog,
                    secondText = MainRes.string.base_success_message
                )
            }

            if (state.value.showFailureRejectDialog) {
                FailureRequestDialog(
                    onDismiss = {
                        viewModel.obtainEvent(MechanicRequestsEvent.CloseMechanicRejectDialogWithFailure)
                    },
                    onExit = {
                        viewModel.obtainEvent(MechanicRequestsEvent.CloseMechanicRejectDialogWithFailure)
                    },
                    firstText = MainRes.string.base_error_message
                )
            }
        }

        when (action.value) {
            is MechanicRequestsAction.ShowSnackBar -> {
                val snackBarAction = action.value as MechanicRequestsAction.ShowSnackBar
                scope.launch {
                    snackBarHostState.showSnackbar(message = snackBarAction.message)
                }
            }

            null -> {}
        }
    }
}