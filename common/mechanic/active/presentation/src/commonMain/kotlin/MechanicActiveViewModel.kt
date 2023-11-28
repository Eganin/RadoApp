import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.ActiveRequestsForMechanicItem
import models.MechanicActiveAction
import models.MechanicActiveEvent
import models.MechanicActiveViewState
import other.BaseSharedViewModel
import other.WrapperForResponse

class MechanicActiveViewModel :
    BaseSharedViewModel<MechanicActiveViewState, MechanicActiveAction, MechanicActiveEvent>(
        initialState = MechanicActiveViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val activeRequestsForMechanicRepository: ActiveRequestsForMechanicRepository =
        Inject.instance()

    init {
        getActiveRequestsByDate()
    }


    override fun obtainEvent(viewEvent: MechanicActiveEvent) {
        when (viewEvent) {

            is MechanicActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)

            is MechanicActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestList(
                errorText = viewEvent.value
            )

            is MechanicActiveEvent.PullRefresh -> getActiveRequestsByDate()

            is MechanicActiveEvent.ArchieveRequest -> archieveRequest()

            is MechanicActiveEvent.OpenDialogInfoRequest -> obtainShowInfoDialog(
                showInfoDialog = true,
                requestId = viewEvent.requestId
            )

            is MechanicActiveEvent.CloseInfoDialog -> obtainShowInfoDialog(showInfoDialog = false)

            is MechanicActiveEvent.StartLoading -> obtainIsLoading(isLoading = true)

            is MechanicActiveEvent.EndLoading -> obtainIsLoading(isLoading = false)

            is MechanicActiveEvent.ShowSuccessDialog -> {
                obtainShowArchieveRequestSuccessDialog(isShow = true)
                obtainEvent(viewEvent=MechanicActiveEvent.CloseInfoDialog)
                obtainEvent(viewEvent=MechanicActiveEvent.PullRefresh)
            }

            is MechanicActiveEvent.CloseSuccessDialog -> {
                obtainShowArchieveRequestSuccessDialog(isShow = false)
                obtainEvent(viewEvent=MechanicActiveEvent.CloseInfoDialog)
                obtainEvent(viewEvent=MechanicActiveEvent.PullRefresh)
            }

            is MechanicActiveEvent.ShowFailureDialog -> obtainShowArchieveRequestFailureDialog(
                isShow = true
            )

            is MechanicActiveEvent.CloseFailureDialog -> obtainShowArchieveRequestFailureDialog(
                isShow = false
            )
        }
    }

    private fun getActiveRequestsByDate(date: String = "") {
        coroutineScope.launch {
            obtainEvent(viewEvent = MechanicActiveEvent.StartLoading)

            val activeRequestsForMechanicItem =
                activeRequestsForMechanicRepository.getRequestsByDate(date = date)
            if (activeRequestsForMechanicItem is ActiveRequestsForMechanicItem.Success) {
                log(tag = TAG) { "Active requests for mechanic by date: ${activeRequestsForMechanicItem.items}" }
                viewState = viewState.copy(
                    requests = activeRequestsForMechanicItem.items
                )
            } else if (activeRequestsForMechanicItem is ActiveRequestsForMechanicItem.Error) {
                log(tag = TAG) { "Active requests for mechanic is failure" }
                obtainEvent(viewEvent = MechanicActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForMechanicItem.message))
            }

            obtainEvent(viewEvent = MechanicActiveEvent.EndLoading)
        }
    }

    private fun archieveRequest() {
        coroutineScope.launch {
            obtainEvent(viewEvent = MechanicActiveEvent.StartLoading)

            val wrapperForResponse =
                activeRequestsForMechanicRepository.archieveRequest(requestId = viewState.requestIdForInfo)

            if (wrapperForResponse is WrapperForResponse.Success) {
                obtainEvent(viewEvent = MechanicActiveEvent.ShowSuccessDialog)
            } else if (wrapperForResponse is WrapperForResponse.Failure) {
                obtainEvent(viewEvent = MechanicActiveEvent.ShowFailureDialog)
            }

            obtainEvent(viewEvent = MechanicActiveEvent.EndLoading)
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