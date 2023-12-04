import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.RejectAction
import models.RejectEvent
import models.RejectRequestViewState
import models.SmallRejectRequestItem
import other.BaseSharedViewModel
import other.Position

class RejectRequestViewModel(private val position: Position) :
    BaseSharedViewModel<RejectRequestViewState, RejectAction, RejectEvent>(
        initialState = RejectRequestViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val rejectRequestsRepositoryForDriverAndObserver: RejectRequestsRepositoryForDriverAndObserver =
        Inject.instance()

    init {
        getRejectRequests()
    }

    override fun obtainEvent(viewEvent: RejectEvent) {
        when (viewEvent) {
            is RejectEvent.PullRefresh -> getRejectRequests()

            is RejectEvent.StartLoading -> obtainIsLoading(isLoading = true)

            is RejectEvent.EndLoading -> obtainIsLoading(isLoading = false)

            is RejectEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestList(errorText = viewEvent.message)

            is RejectEvent.OpenRecreateDialog -> obtainShowRecreateDialog(
                showRecreateDialog = true,
                requestId = viewEvent.requestId
            )

            is RejectEvent.CloseRecreateDialog -> {
                obtainShowRecreateDialog(showRecreateDialog = false)
                obtainEvent(viewEvent = RejectEvent.PullRefresh)
            }

            is RejectEvent.OpenInfoDialog -> obtainShowInfoDialog(
                showInfoDialog = true,
                requestId = viewEvent.requestId
            )

            is RejectEvent.CloseInfoDialog -> {
                obtainShowInfoDialog(showInfoDialog = false)
                obtainEvent(viewEvent = RejectEvent.PullRefresh)
            }
        }
    }

    private fun getRejectRequests() {
        coroutineScope.launch {
            obtainEvent(viewEvent = RejectEvent.StartLoading)

            val smallRejectRequestItem =
                if (position == Position.DRIVER) rejectRequestsRepositoryForDriverAndObserver.getRejectRequestsForDriver()
                else rejectRequestsRepositoryForDriverAndObserver.getRejectRequestsForObserver()


            if (smallRejectRequestItem is SmallRejectRequestItem.Success) {
                log(tag = TAG) { "Reject requests: ${smallRejectRequestItem.items}" }
                obtainEvent(viewEvent = RejectEvent.ErrorTextForRequestListChanged(""))
                viewState = viewState.copy(requests = smallRejectRequestItem.items)
            } else if (smallRejectRequestItem is SmallRejectRequestItem.Error) {
                log(tag = TAG) { "Reject requests id failure" }
                viewState = viewState.copy(errorTextForRequestList = smallRejectRequestItem.message)
            }

            obtainEvent(viewEvent = RejectEvent.EndLoading)
        }
    }

    private fun obtainShowRecreateDialog(showRecreateDialog: Boolean, requestId: Int? = null) {
        viewState = viewState.copy(showRecreateDialog = showRecreateDialog)
        requestId?.let {
            viewState = viewState.copy(requestIdForInfo = requestId)
        }
    }

    private fun obtainShowInfoDialog(showInfoDialog: Boolean, requestId: Int? = null) {
        viewState = viewState.copy(showInfoDialog = showInfoDialog)
        requestId?.let {
            viewState = viewState.copy(requestIdForInfo = requestId)
        }
    }

    private fun obtainIsLoading(isLoading: Boolean) {
        viewState = viewState.copy(isLoading = isLoading)
    }

    private fun obtainErrorTextForRequestList(errorText: String) {
        viewState = viewState.copy(errorTextForRequestList = errorText)
    }

    private companion object {
        const val TAG = "RejectRequestViewModel"
    }
}