package models

sealed class MechanicRequestsEvent {

    data class OpenDialogInfoRequest(val requestId: Int) : MechanicRequestsEvent()

    data object CloseInfoDialog : MechanicRequestsEvent()

    data object ChooseDateTime : MechanicRequestsEvent()

    data class ReopenDialogInfoRequest(val requestId: Int, val datetime: String) :
        MechanicRequestsEvent()

    data object RejectRequest : MechanicRequestsEvent()

    data class ConfirmationRequest(val requestId: Int, val time: String, val date: String) :
        MechanicRequestsEvent()

    data class ErrorTextForRequestListChanged(val message: String) : MechanicRequestsEvent()
}