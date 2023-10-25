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
import models.UnconfirmedRequestsItem
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

    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository = Inject.instance()

    init {
        getUnconfirmedRequests()
        getActiveRequestsByDate()
    }

    override fun obtainEvent(viewEvent: DriverActiveEvent) {
        when (viewEvent) {
            is DriverActiveEvent.OpenDialogCreateRequest -> openCreateRequestScreen()
            is DriverActiveEvent.OpenDialogInfoRequest -> openInfoRequestScreen(
                requestId = viewEvent.requestId,
                isActiveDialog = viewEvent.isActiveRequest
            )

            is DriverActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)
            is DriverActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestListChange(
                errorMessage = viewEvent.value
            )

            is DriverActiveEvent.CloseCreateDialog -> obtainCloseCreateDialogChange()
            is DriverActiveEvent.CloseInfoDialog -> obtainCloseInfoDialogChange()
            is DriverActiveEvent.PullRefresh -> {
                getUnconfirmedRequests()
                getActiveRequestsByDate()
            }
        }
    }

    private fun changeLoadingForActiveRequests() {
        viewState = viewState.copy(isLoadingActiveRequests = !viewState.isLoadingActiveRequests)
    }

    private fun changeLoadingForUnconfirmedRequests() {
        viewState =
            viewState.copy(isLoadingUnconfirmedRequests = !viewState.isLoadingUnconfirmedRequests)
    }

    private fun openCreateRequestScreen() {
        log(tag = TAG) { "Navigate to create request screen" }
        viewState = viewState.copy(showCreateDialog = !viewState.showCreateDialog)
    }

    private fun openInfoRequestScreen(requestId: Int, isActiveDialog: Boolean) {
        log(tag = TAG) { "Navigate to info request screen" }
        viewState =
            viewState.copy(
                requestIdForInfo = requestId,
                showInfoDialog = !viewState.showInfoDialog,
                isActiveDialog = isActiveDialog
            )
    }

    private fun getActiveRequestsByDate(date: String = "") {
        coroutineScope.launch {
            changeLoadingForActiveRequests()
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
            changeLoadingForActiveRequests()
        }
    }

    private fun getUnconfirmedRequests() {
        coroutineScope.launch {
            changeLoadingForUnconfirmedRequests()
            val unconfirmedRequestsForDriverItem =
                unconfirmedRequestsRepository.getRequests(isDriver = true)
            if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsItem.Success) {
                log(tag = TAG) { "Unconfirmed requests" + unconfirmedRequestsForDriverItem.items.toString() }
                viewState = viewState.copy(
                    unconfirmedRequests = unconfirmedRequestsForDriverItem.items
                )
            } else if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsItem.Error) {
                log(tag = TAG) { "Unconfirmed Requests failure" }
                obtainEvent(viewEvent = DriverActiveEvent.ErrorTextForRequestListChanged(value = unconfirmedRequestsForDriverItem.message))
            }
            changeLoadingForUnconfirmedRequests()
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
        //update requests list after close create dialog
        getUnconfirmedRequests()
        getActiveRequestsByDate()
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