package models

sealed class CreateRequestEvent {

    data object CreateRequest : CreateRequestEvent()

    data class SelectedTypeVehicleChanged(val value: VehicleType) : CreateRequestEvent()

    data class NumberVehicleChanged(val value: String) : CreateRequestEvent()

    data class FaultDescriptionChanged(val value: String) : CreateRequestEvent()
}