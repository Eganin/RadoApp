import di.Inject
import io.github.aakira.napier.log
import kotlinx.coroutines.*
import models.*
import other.BaseSharedViewModel

class DriverActiveViewModel : BaseSharedViewModel<DriverActiveViewState, DriverActiveAction, DriverActiveEvent>(
    initialState = DriverActiveViewState()
) {

    private val coroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default +
                CoroutineExceptionHandler { _, throwable ->
                    log(tag = TAG) { throwable.message ?: "Error" }
                })

    private val activeRequestsRepository: ActiveRequestsForDriverRepository = Inject.instance()

    init {
        getActiveRequests()
    }

    override fun obtainEvent(viewEvent: DriverActiveEvent) {
        when (viewEvent) {
            is DriverActiveEvent.SelectedDateChanged -> {}
            is DriverActiveEvent.CreateRequest -> {}
            is DriverActiveEvent.SelectedTypeVehicleChanged -> obtainSelectedTypeVehicleChange(typeVehicle = viewEvent.value)
            is DriverActiveEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is DriverActiveEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(faultDescription = viewEvent.value)
            is DriverActiveEvent.ErrorTextForRequestListChanged -> obtainErrorTextForRequestListChange(errorMessage = viewEvent.value)
        }
    }

    private fun getActiveRequests() {
        coroutineScope.launch {
            val activeRequestsForDriverItem = activeRequestsRepository.getRequestsByDate(date = "")
            if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Success) {
                viewState = viewState.copy(
                    requests = activeRequestsForDriverItem.items
                )
            } else if (activeRequestsForDriverItem is ActiveRequestsForDriverItem.Error) {
                obtainEvent(viewEvent = DriverActiveEvent.ErrorTextForRequestListChanged(value = activeRequestsForDriverItem.message))
            }
        }
    }

    private fun obtainErrorTextForRequestListChange(errorMessage: String) {
        viewState = viewState.copy(
            errorTextForRequestList = errorMessage
        )
    }

    private fun obtainSelectedTypeVehicleChange(typeVehicle: VehicleType) {
        viewState = viewState.copy(
            selectedVehicleType = typeVehicle
        )
    }

    private fun obtainNumberVehicleChange(numberVehicle: String) {
        viewState = viewState.copy(
            numberVehicle = numberVehicle
        )
    }

    private fun obtainFaultDescriptionChange(faultDescription: String) {
        viewState = viewState.copy(
            faultDescription = faultDescription
        )
    }

    private companion object {
        const val TAG = "DriverActiveViewModel"
    }
}