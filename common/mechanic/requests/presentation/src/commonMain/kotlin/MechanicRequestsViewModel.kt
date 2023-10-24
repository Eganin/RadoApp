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
import time.convertDateAndHoursAndMinutesToString

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
        when (viewEvent) {
            is MechanicRequestsEvent.OpenDialogInfoRequest -> obtainShowInfoDialogChanged(requestId = viewEvent.requestId)
            is MechanicRequestsEvent.ConfirmationRequest -> {}
            is MechanicRequestsEvent.RejectRequest -> {}
            is MechanicRequestsEvent.ErrorTextForRequestListChanged -> {}
            is MechanicRequestsEvent.CloseInfoDialog -> obtainShowInfoDialogChanged()
            is MechanicRequestsEvent.OpenDatePicker -> obtainShowDatePickerChanged()
            is MechanicRequestsEvent.OpenTimePicker -> obtainShowTimePickerChangedAndDate(date = viewEvent.date)
            is MechanicRequestsEvent.CloseDatePicker -> obtainShowDatePickerChanged()
            is MechanicRequestsEvent.CloseTimePicker -> obtainShowTimePickerChanged()
            is MechanicRequestsEvent.ReopenDialogInfoRequest -> obtainReopenInfoDialogChanged()
            is MechanicRequestsEvent.SubmitDateTime -> submitDateTime(
                hour = viewEvent.hour,
                minute = viewEvent.minute
            )
        }
    }

    private fun getUnconfirmedRequests() {
        coroutineScope.launch {
            changeLoading()
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
            changeLoading()
        }
    }

    private fun obtainReopenInfoDialogChanged() {
        viewState = viewState.copy(
            reopenDialog = !viewState.reopenDialog
        )
    }

    private fun obtainShowInfoDialogChanged() {
        viewState = viewState.copy(
            showInfoDialog = !viewState.showInfoDialog
        )
    }

    private fun submitDateTime(hour: Int, minute: Int) {
        val (datetimeForView, datetimeForServer) = convertDateAndHoursAndMinutesToString(
            date = viewState.date,
            hour = hour,
            minute = minute
        )
        viewState = viewState.copy(
            datetime = datetimeForView,
            datetimeForServer = datetimeForServer
        )
    }

    private fun obtainShowDatePickerChanged() {
        viewState = viewState.copy(
            showDatePicker = !viewState.showDatePicker
        )
    }

    private fun obtainShowTimePickerChangedAndDate(date: Long) {
        viewState = viewState.copy(
            showTimePicker = !viewState.showTimePicker,
            date = date
        )
    }

    private fun obtainShowTimePickerChanged() {
        viewState = viewState.copy(
            showTimePicker = !viewState.showTimePicker
        )
    }

    private fun obtainShowInfoDialogChanged(requestId: Int) {
        viewState = viewState.copy(
            showInfoDialog = !viewState.showInfoDialog,
            requestsIdForInfo = requestId
        )
    }

    private fun changeLoading() {
        viewState = viewState.copy(isLoading = !viewState.isLoading)
    }

    private companion object {
        const val TAG = "MechanicRequestsViewModel"
    }
}