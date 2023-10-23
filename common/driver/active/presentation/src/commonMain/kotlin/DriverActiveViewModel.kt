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
import models.UnconfirmedRequestsForDriverItem
import other.BaseSharedViewModel

class DriverActiveViewModel :
    BaseSharedViewModel<DriverActiveViewState, DriverActiveAction, DriverActiveEvent>(
        initialState = DriverActiveViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsRepository: ActiveRequestsForDriverRepository = Inject.instance()

    private val unconfirmedRequestsRepository: UnconfirmedRequestsForDriverRepository =
        Inject.instance()

    init {
        getUnconfirmedRequests()
        getActiveRequestsByDate()
    }

    override fun obtainEvent(viewEvent: DriverActiveEvent) {
        when (viewEvent) {
            is DriverActiveEvent.OpenDialogCreateRequest -> openCreateRequestScreen()
            is DriverActiveEvent.OpenDialogInfoRequest -> openInfoRequestScreen(requestId = viewEvent.requestId)
            is DriverActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)
            is DriverActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestListChange(
                errorMessage = viewEvent.value
            )

            is DriverActiveEvent.CloseCreateDialog -> obtainCloseCreateDialogChange()
            is DriverActiveEvent.CloseInfoDialog -> obtainCloseInfoDialogChange()
        }
    }

    private fun openCreateRequestScreen() {
        log(tag = TAG) { "Navigate to create request screen" }
        viewState = viewState.copy(showCreateDialog = !viewState.showCreateDialog)
    }

    private fun openInfoRequestScreen(requestId: Int) {
        log(tag = TAG) { "Navigate to info request screen" }
        viewState =
            viewState.copy(requestIdForInfo = requestId, showInfoDialog = !viewState.showInfoDialog)
    }

    private fun getActiveRequestsByDate(date: String = "") {
        coroutineScope.launch {
            val activeRequestsForDriverItem =
                activeRequestsRepository.getRequestsByDate(date = date)
            if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Success) {
                log(tag = TAG) { "Active requests by date" + activeRequestsForDriverItem.items.toString() }
                viewState = viewState.copy(
                    requests = activeRequestsForDriverItem.items
                )
            } else if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Error) {
                log(tag = TAG) { "Active Requests is failure" }
                obtainEvent(viewEvent = DriverActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForDriverItem.message))
            }
        }
    }

    private fun getUnconfirmedRequests() {
        coroutineScope.launch {
            val unconfirmedRequestsForDriverItem = unconfirmedRequestsRepository.getRequests()
            if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsForDriverItem.Success) {
                log(tag = TAG) { "Unconfirmed requests" + unconfirmedRequestsForDriverItem.items.toString() }
                viewState = viewState.copy(
                    unconfirmedRequests = unconfirmedRequestsForDriverItem.items
                )
            } else if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsForDriverItem.Error) {
                log(tag = TAG) { "Unconfirmed Requests failure" }
                obtainEvent(viewEvent = DriverActiveEvent.ErrorTextForRequestListChanged(value = unconfirmedRequestsForDriverItem.message))
            }
        }
    }

    private fun obtainCloseInfoDialogChange() {
        log(tag = TAG) { "Close info dialog" }
        viewState = viewState.copy(
            showInfoDialog = !viewState.showInfoDialog
        )
    }

    private fun obtainCloseCreateDialogChange() {
        log(tag = TAG) { "CLOSE CREATE DIALOG" }
        viewState = viewState.copy(
            showCreateDialog = !viewState.showCreateDialog
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