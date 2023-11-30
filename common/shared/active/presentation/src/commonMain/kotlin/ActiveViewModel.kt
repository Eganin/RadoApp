import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.ActiveAction
import models.ActiveEvent
import models.ActiveRequestsForMechanicItem
import models.ActiveRequestsForObserverItem
import models.ActiveViewState
import other.BaseSharedViewModel
import other.Position
import other.WrapperForResponse

class ActiveViewModel(private val position: Position) :
    BaseSharedViewModel<ActiveViewState, ActiveAction, ActiveEvent>(
        initialState = ActiveViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsForMechanicRepository: ActiveRequestsForMechanicRepository =
        Inject.instance()

    private val activeRequestsForObserverRepository: ActiveRequestsForObserverRepository =
        Inject.instance()

    init {
        getActiveRequestsByDate()
    }


    override fun obtainEvent(viewEvent: ActiveEvent) {
        when (viewEvent) {

            is ActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)

            is ActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestList(
                errorText = viewEvent.value
            )

            is ActiveEvent.PullRefresh -> getActiveRequestsByDate()

            is ActiveEvent.ArchieveRequest -> archieveRequest()

            is ActiveEvent.OpenDialogInfoRequest -> obtainShowInfoDialog(
                showInfoDialog = true,
                requestId = viewEvent.requestId
            )

            is ActiveEvent.CloseInfoDialog -> obtainShowInfoDialog(showInfoDialog = false)

            is ActiveEvent.StartLoading -> obtainIsLoading(isLoading = true)

            is ActiveEvent.EndLoading -> obtainIsLoading(isLoading = false)

            is ActiveEvent.ShowSuccessDialog -> {
                obtainShowArchieveRequestSuccessDialog(isShow = true)
                obtainEvent(viewEvent = ActiveEvent.CloseInfoDialog)
                obtainEvent(viewEvent = ActiveEvent.PullRefresh)
            }

            is ActiveEvent.CloseSuccessDialog -> {
                obtainShowArchieveRequestSuccessDialog(isShow = false)
                obtainEvent(viewEvent = ActiveEvent.CloseInfoDialog)
                obtainEvent(viewEvent = ActiveEvent.PullRefresh)
            }

            is ActiveEvent.ShowFailureDialog -> obtainShowArchieveRequestFailureDialog(
                isShow = true
            )

            is ActiveEvent.CloseFailureDialog -> obtainShowArchieveRequestFailureDialog(
                isShow = false
            )
        }
    }

    private fun getActiveRequestsByDate(date: String = "") {
        when (position) {
            Position.MECHANIC -> getActiveRequestsForMechanic(date = date)
            else -> getActiveRequestsForObserver(date = date)
        }
    }

    private fun getActiveRequestsForMechanic(date: String) {
        coroutineScope.launch {
            obtainEvent(viewEvent = ActiveEvent.StartLoading)

            val activeRequestsForMechanicItem =
                activeRequestsForMechanicRepository.getRequestsByDate(date = date)
            if (activeRequestsForMechanicItem is ActiveRequestsForMechanicItem.Success) {
                log(tag = TAG) { "Active requests for mechanic by date: ${activeRequestsForMechanicItem.items}" }
                obtainEvent(viewEvent = ActiveEvent.ErrorTextForRequestListChanged(value = ""))
                viewState = viewState.copy(
                    requestsForMechanic = activeRequestsForMechanicItem.items
                )
            } else if (activeRequestsForMechanicItem is ActiveRequestsForMechanicItem.Error) {
                log(tag = TAG) { "Active requests for mechanic is failure" }
                obtainEvent(viewEvent = ActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForMechanicItem.message))
            }

            obtainEvent(viewEvent = ActiveEvent.EndLoading)
        }
    }

    private fun getActiveRequestsForObserver(date: String) {
        coroutineScope.launch {
            obtainEvent(viewEvent = ActiveEvent.StartLoading)

            val activeRequestsForObserverItem =
                activeRequestsForObserverRepository.getRequestsByDate(date = date)
            if (activeRequestsForObserverItem is ActiveRequestsForObserverItem.Success) {
                log(tag = TAG) { "Active requests for observer by date: ${activeRequestsForObserverItem.items}" }
                obtainEvent(viewEvent = ActiveEvent.ErrorTextForRequestListChanged(value = ""))
                viewState = viewState.copy(
                    requestsForObserver = activeRequestsForObserverItem.items
                )
            } else if (activeRequestsForObserverItem is ActiveRequestsForObserverItem.Error) {
                log(tag = TAG) { "Active requests for mechanic is failure" }
                obtainEvent(viewEvent = ActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForObserverItem.message))
            }

            obtainEvent(viewEvent = ActiveEvent.EndLoading)
        }
    }

    private fun archieveRequest() {
        coroutineScope.launch {
            obtainEvent(viewEvent = ActiveEvent.StartLoading)

            val wrapperForResponse =
                activeRequestsForMechanicRepository.archieveRequest(requestId = viewState.requestIdForInfo)

            if (wrapperForResponse is WrapperForResponse.Success) {
                obtainEvent(viewEvent = ActiveEvent.ShowSuccessDialog)
            } else if (wrapperForResponse is WrapperForResponse.Failure) {
                obtainEvent(viewEvent = ActiveEvent.ShowFailureDialog)
            }

            obtainEvent(viewEvent = ActiveEvent.EndLoading)
        }
    }

    private fun obtainIsLoading(isLoading: Boolean) {
        viewState = viewState.copy(isLoading = isLoading)
    }

    private fun obtainShowInfoDialog(showInfoDialog: Boolean, requestId: Int? = null) {
        viewState = viewState.copy(showInfoDialog = showInfoDialog)
        requestId?.let {
            viewState = viewState.copy(requestIdForInfo = it)
        }
    }

    private fun obtainErrorTextForRequestList(errorText: String) {
        viewState = viewState.copy(errorTextForRequestList = errorText)
    }

    private fun obtainShowArchieveRequestSuccessDialog(isShow: Boolean) {
        viewState = viewState.copy(showArchieveRequestSuccessDialog = isShow)
    }

    private fun obtainShowArchieveRequestFailureDialog(isShow: Boolean) {
        viewState = viewState.copy(showArchieveRequestFailureDialog = isShow)
    }

    private companion object {
        const val TAG = "MechanicActiveViewModel"
    }
}