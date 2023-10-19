import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.ActiveRequestsForDriverItem
import models.DriverActiveAction
import models.DriverActiveEvent
import models.DriverActiveViewState
import other.BaseSharedViewModel

class DriverActiveViewModel : BaseSharedViewModel<DriverActiveViewState, DriverActiveAction, DriverActiveEvent>(
    initialState = DriverActiveViewState()
) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsRepository: ActiveRequestsForDriverRepository = Inject.instance()

    init {
        getActiveRequestsByDate()
    }

    override fun obtainEvent(viewEvent: DriverActiveEvent) {
        when (viewEvent) {
            is DriverActiveEvent.OpenDialogCreateRequest -> openCreateRequestScreen()
            is DriverActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)
            is DriverActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestListChange(errorMessage = viewEvent.value)
            is DriverActiveEvent.CloseCreateDialog -> obtainCloseCreateDialogChange(isCloseDialog = viewEvent.value)
        }
    }

    private fun openCreateRequestScreen() {
        log(tag = TAG) { "Navigate to create request screen" }
        viewState = viewState.copy(showCreateDialog = !viewState.showCreateDialog)
    }

    private fun getActiveRequestsByDate(date: String = "") {
        coroutineScope.launch {
            val activeRequestsForDriverItem = activeRequestsRepository.getRequestsByDate(date = date)
            if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Success) {
                log(tag = TAG) { activeRequestsForDriverItem.items.toString() }
                viewState = viewState.copy(
                    requests = activeRequestsForDriverItem.items
                )
            } else if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Error) {
                log(tag = TAG) { "Active Requests without date is failure" }
                obtainEvent(viewEvent = DriverActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForDriverItem.message))
            }
        }
    }

    private fun obtainCloseCreateDialogChange(isCloseDialog: Boolean) {
        log(tag=TAG) { "CLOSE DIALOG" }
        viewState = viewState.copy(
            showCreateDialog = isCloseDialog
        )
    }

    private fun obtainErrorTextForRequestListChange(errorMessage: String) {
        viewState = viewState.copy(
            errorTextForRequestList = errorMessage
        )
        log(tag = TAG) { "Error message for requests setup" }
    }

    private companion object {
        const val TAG = "DriverActiveViewModel"
    }
}