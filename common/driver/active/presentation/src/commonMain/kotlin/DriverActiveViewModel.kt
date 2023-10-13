import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.*
import models.*
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
            is DriverActiveEvent.CreateRequest -> createRequest()
            is DriverActiveEvent.SelectedDateChanged -> getActiveRequestsByDate(date = viewEvent.value)
            is DriverActiveEvent.SelectedTypeVehicleChanged -> obtainSelectedTypeVehicleChange(typeVehicle = viewEvent.value)
            is DriverActiveEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is DriverActiveEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(faultDescription = viewEvent.value)
            is DriverActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestListChange(errorMessage = viewEvent.value)
        }
    }

    private fun createRequest() {
        coroutineScope.launch {
            val createRequestIdItem = activeRequestsRepository.createRequest(
                typeVehicle = viewState.selectedVehicleType.nameVehicleType,
                numberVehicle = viewState.numberVehicle,
                faultDescription = viewState.faultDescription
            )
            if (createRequestIdItem is CreateRequestIdItem.Success) {
                log(tag = TAG) { "Create request is success" }
                viewAction = DriverActiveAction.ShowSuccessCreateRequestDialog
            } else if (createRequestIdItem is CreateRequestIdItem.Error) {
                log(tag = TAG) { "Create request is failure" }
                viewAction = DriverActiveAction.ShowErrorSnackBar(message = createRequestIdItem.message)
            }
        }
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

    private fun obtainErrorTextForRequestListChange(errorMessage: String) {
        viewState = viewState.copy(
            errorTextForRequestList = errorMessage
        )
        log(tag = TAG) { "Error message for requests setup" }
    }

    private fun obtainSelectedTypeVehicleChange(typeVehicle: VehicleType) {
        viewState = viewState.copy(
            selectedVehicleType = typeVehicle
        )
        log(tag = TAG) { "Type vehicle is changed" }
    }

    private fun obtainNumberVehicleChange(numberVehicle: String) {
        viewState = viewState.copy(
            numberVehicle = numberVehicle
        )
        log(tag = TAG) { "Number vehicle is changed" }
    }

    private fun obtainFaultDescriptionChange(faultDescription: String) {
        viewState = viewState.copy(
            faultDescription = faultDescription
        )
        log(tag = TAG) { "Fault description is changed" }
    }

    private companion object {
        const val TAG = "DriverActiveViewModel"
    }
}