import models.DriverActiveAction
import models.DriverActiveEvent
import models.DriverActiveViewState
import models.VehicleType
import other.BaseSharedViewModel

class DriverActiveViewModel : BaseSharedViewModel<DriverActiveViewState, DriverActiveAction, DriverActiveEvent>(
    initialState = DriverActiveViewState()
) {
    override fun obtainEvent(viewEvent: DriverActiveEvent) {
        when (viewEvent) {
            is DriverActiveEvent.SelectedDateChanged -> {}
            is DriverActiveEvent.CreateRequest -> {}
            is DriverActiveEvent.SelectedTypeVehicleChanged -> obtainSelectedTypeVehicleChange(typeVehicle = viewEvent.value)
            is DriverActiveEvent.NumberVehicleChanged -> obtainNumberVehicleChange(numberVehicle = viewEvent.value)
            is DriverActiveEvent.FaultDescriptionChanged -> obtainFaultDescriptionChange(faultDescription = viewEvent.value)
        }
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
}