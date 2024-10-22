package models

sealed class MechanicRequestsEvent {

    data class OpenDialogInfoRequest(val requestId: Int) : MechanicRequestsEvent()

    data object CloseInfoDialog : MechanicRequestsEvent()

    data object OpenDatePicker: MechanicRequestsEvent()

    data class OpenTimePicker(val date: Long) : MechanicRequestsEvent()

    data object CloseDatePicker: MechanicRequestsEvent()

    data object CloseTimePicker : MechanicRequestsEvent()

    data object CloseSuccessDialog : MechanicRequestsEvent()

    data object CloseFailureDialog : MechanicRequestsEvent()

    data class SubmitDateTime(val hour: Int,val minute:Int): MechanicRequestsEvent()

    data object ReopenDialogInfoRequest : MechanicRequestsEvent()

    data object ShowRejectRequest : MechanicRequestsEvent()

    data object ConfirmationRequest : MechanicRequestsEvent()

    data class ErrorTextForRequestListChanged(val message: String) : MechanicRequestsEvent()

    data object DriverPhoneClick : MechanicRequestsEvent()

    data object PullRefresh: MechanicRequestsEvent()

    data object ClearState: MechanicRequestsEvent()

    data object SendRejectRequest:MechanicRequestsEvent()

    data class CommentMechanicValueChange(val commentMechanic:String): MechanicRequestsEvent()

    data object CloseMechanicRejectDialogWithSuccess : MechanicRequestsEvent()
    data object CloseMechanicRejectDialogWithFailure : MechanicRequestsEvent()

    data object CloseMechanicRejectDialog : MechanicRequestsEvent()

    data class CheckRepairOnBase(val isChecked:Boolean):MechanicRequestsEvent()

    data class CheckRepairOnOtherPlace(val isChecked: Boolean): MechanicRequestsEvent()

    data class StreetForRepairChanged(val street:String):MechanicRequestsEvent()
}