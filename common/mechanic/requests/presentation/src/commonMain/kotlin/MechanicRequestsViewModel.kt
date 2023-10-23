import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import models.MechanicRequestsAction
import models.MechanicRequestsEvent
import models.MechanicRequestsViewState
import models.UnconfirmedRequestsItem
import other.BaseSharedViewModel

class MechanicRequestsViewModel :
    BaseSharedViewModel<MechanicRequestsViewState, MechanicRequestsAction, MechanicRequestsEvent>(
        initialState = MechanicRequestsViewState()
    ) {
    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default + CoroutineExceptionHandler { _, throwable ->
            log(tag = TAG) { throwable.message ?: "Error" }
        })

    private val unconfirmedRequestsRepository: UnconfirmedRequestsRepository = Inject.instance()

    init {
        getUnconfirmedRequests()
    }

    override fun obtainEvent(viewEvent: MechanicRequestsEvent) {
        TODO("Not yet implemented")
    }

    private fun getUnconfirmedRequests() {
        coroutineScope.launch {
            val unconfirmedRequestsForDriverItem =
                unconfirmedRequestsRepository.getRequests(isDriver = false)
            if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsItem.Success) {
                log(tag = TAG) { "Unconfirmed requests" + unconfirmedRequestsForDriverItem.items.toString() }
                viewState = viewState.copy(
                    unconfirmedRequests = unconfirmedRequestsForDriverItem.items
                )
            } else if (unconfirmedRequestsForDriverItem is UnconfirmedRequestsItem.Error) {
                log(tag = TAG) { "Unconfirmed Requests failure" }
                obtainEvent(viewEvent = MechanicRequestsEvent.ErrorTextForRequestListChanged(message = unconfirmedRequestsForDriverItem.message))
            }
        }
    }

    private companion object {
        const val TAG = "MechanicRequestsViewModel"
    }
}