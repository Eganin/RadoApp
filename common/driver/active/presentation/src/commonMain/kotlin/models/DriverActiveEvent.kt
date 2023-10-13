package models

sealed class DriverActiveEvent {

    data class SelectedDateChanged(val value: String) : DriverActiveEvent()

    data object CreateRequest : DriverActiveEvent()

    data object OpenDialogCreateRequest : DriverActiveEvent()

    data class SelectedTypeVehicleChanged(val value: VehicleType) : DriverActiveEvent()

    data class NumberVehicleChanged(val value: String) : DriverActiveEvent()

    data class FaultDescriptionChanged(val value: String) : DriverActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String): DriverActiveEvent()
}