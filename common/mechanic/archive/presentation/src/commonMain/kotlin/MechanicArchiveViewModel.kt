import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.ArchiveRequestsForMechanicItem
import models.MechanicArchiveAction
import models.MechanicArchiveEvent
import models.MechanicArchiveViewState
import other.BaseSharedViewModel

class MechanicArchiveViewModel :
    BaseSharedViewModel<MechanicArchiveViewState, MechanicArchiveAction, MechanicArchiveEvent>(
        initialState = MechanicArchiveViewState()
    ) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val archiveRequestsForMechanicRepository: ArchiveRequestsForMechanicRepository =
        Inject.instance()

    init {
        getArchiveRequests()
    }

    override fun obtainEvent(viewEvent: MechanicArchiveEvent) {
        when (viewEvent) {
            is MechanicArchiveEvent.PullRefresh -> getArchiveRequests()

            is MechanicArchiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestList(
                errorText = viewEvent.message
            )

            is MechanicArchiveEvent.OpenDialogInfoRequest -> obtainShowInfoDialog(
                showInfoDialog = true,
                requestId = viewEvent.requestId
            )

            is MechanicArchiveEvent.CloseInfoDialog -> obtainShowInfoDialog(showInfoDialog = false)

            is MechanicArchiveEvent.StartLoading -> obtainIsLoading(isLoading = true)

            is MechanicArchiveEvent.EndLoading -> obtainIsLoading(isLoading = false)
        }
    }

    private fun getArchiveRequests(){
        coroutineScope.launch {
            obtainEvent(viewEvent = MechanicArchiveEvent.StartLoading)

            val archiveRequestsForMechanicItem = archiveRequestsForMechanicRepository.getArchiveRequests()

            if (archiveRequestsForMechanicItem is ArchiveRequestsForMechanicItem.Success){
                log(tag= TAG) { "Archive requests for mechanic: ${archiveRequestsForMechanicItem.items}" }
                viewState=viewState.copy(
                    requests = archiveRequestsForMechanicItem.items
                )
            }else if(archiveRequestsForMechanicItem is ArchiveRequestsForMechanicItem.Error){
                log(tag= TAG) { "Archive requests for mechanic is failure" }
                viewState=viewState.copy(errorTextForRequestList = archiveRequestsForMechanicItem.message)
            }

            obtainEvent(viewEvent = MechanicArchiveEvent.EndLoading)
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