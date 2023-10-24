package models

sealed class MechanicRequestsEvent {

    data class OpenDialogInfoRequest(val requestId: Int) : MechanicRequestsEvent()

    data object CloseInfoDialog : MechanicRequestsEvent()

    data object OpenDatePicker: MechanicRequestsEvent()

    data class OpenTimePicker(val date: Long) : MechanicRequestsEvent()

    data object CloseDatePicker: MechanicRequestsEvent()

    data object CloseTimePicker : MechanicRequestsEvent()

    data class SubmitDateTime(val hour: Int,val minute:Int): MechanicRequestsEvent()

    data object ReopenDialogInfoRequest : MechanicRequestsEvent()

    data object RejectRequest : MechanicRequestsEvent()

    data object ConfirmationRequest : MechanicRequestsEvent()

    data class ErrorTextForRequestListChanged(val message: String) : MechanicRequestsEvent()

    data object DriverPhoneClick : MechanicRequestsEvent()
}