package models

sealed class DriverActiveEvent {

    data class SelectedDateChanged(val value: String) : DriverActiveEvent()

    data object OpenDialogCreateRequest : DriverActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String): DriverActiveEvent()

    data class CloseCreateDialog(val value : Boolean): DriverActiveEvent()
}