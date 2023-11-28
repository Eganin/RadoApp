import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.ArchiveAction
import models.ArchiveEvent
import models.ArchiveRequestsForDriverItem
import models.ArchiveRequestsForMechanicItem
import models.ArchiveViewState
import other.BaseSharedViewModel
import other.Position

class ArchiveViewModel(private val position: Position) :
    BaseSharedViewModel<ArchiveViewState, ArchiveAction, ArchiveEvent>(
        initialState = ArchiveViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val archiveRequestsForMechanicRepository: ArchiveRequestsForMechanicRepository =
        Inject.instance()

    private val archiveRequestsForDriverRepository: ArchiveRequestsForDriverRepository =
        Inject.instance()

    init {
        getArchiveRequests()
    }

    override fun obtainEvent(viewEvent: ArchiveEvent) {
        when (viewEvent) {
            is ArchiveEvent.PullRefresh -> getArchiveRequests()

            is ArchiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestList(
                errorText = viewEvent.message
            )

            is ArchiveEvent.OpenDialogInfoRequest -> obtainShowInfoDialog(
                showInfoDialog = true,
                requestId = viewEvent.requestId
            )

            is ArchiveEvent.CloseInfoDialog -> obtainShowInfoDialog(showInfoDialog = false)

            is ArchiveEvent.StartLoading -> obtainIsLoading(isLoading = true)

            is ArchiveEvent.EndLoading -> obtainIsLoading(isLoading = false)
        }
    }

    private fun getArchiveRequests() {
        when (position) {
            Position.DRIVER -> getArchiveRequestsForDriver()
            Position.MECHANIC -> getArchiveRequestsForMechanic()
            else -> getArchiveRequestsForObserver()
        }
    }

    private fun getArchiveRequestsForObserver() {}

    private fun getArchiveRequestsForDriver() {
        coroutineScope.launch {
            obtainEvent(viewEvent = ArchiveEvent.StartLoading)

            val archiveRequestsForDriverItem =
                archiveRequestsForDriverRepository.getArchiveRequests()

            if (archiveRequestsForDriverItem is ArchiveRequestsForDriverItem.Success) {
                log(tag = TAG) { "Archive requests for driver: ${archiveRequestsForDriverItem.items}" }
                viewState = viewState.copy(
                    requestsForDriver = archiveRequestsForDriverItem.items
                )
            } else if (archiveRequestsForDriverItem is ArchiveRequestsForDriverItem.Error) {
                log(tag = TAG) { "Archive requests for mechanic is failure" }
                viewState =
                    viewState.copy(errorTextForRequestList = archiveRequestsForDriverItem.message)
            }

            obtainEvent(viewEvent = ArchiveEvent.EndLoading)
        }
    }

    private fun getArchiveRequestsForMechanic() {
        coroutineScope.launch {
            obtainEvent(viewEvent = ArchiveEvent.StartLoading)

            val archiveRequestsForMechanicItem =
                archiveRequestsForMechanicRepository.getArchiveRequests()

            if (archiveRequestsForMechanicItem is ArchiveRequestsForMechanicItem.Success) {
                log(tag = TAG) { "Archive requests for mechanic: ${archiveRequestsForMechanicItem.items}" }
                viewState = viewState.copy(
                    requestsForMechanic = archiveRequestsForMechanicItem.items
                )
            } else if (archiveRequestsForMechanicItem is ArchiveRequestsForMechanicItem.Error) {
                log(tag = TAG) { "Archive requests for mechanic is failure" }
                viewState =
                    viewState.copy(errorTextForRequestList = archiveRequestsForMechanicItem.message)
            }

            obtainEvent(viewEvent = ArchiveEvent.EndLoading)
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

    private companion object {
        const val TAG = "MechanicArchiveViewModel"
    }
}