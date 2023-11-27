package models

sealed class MechanicActiveEvent {

    data class SelectedDateChanged(val value: String) : MechanicActiveEvent()

    data class OpenDialogInfoRequest(val requestId: Int) : MechanicActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String) : MechanicActiveEvent()

    data object ArchieveRequest:MechanicActiveEvent()

    data object CloseInfoDialog : MechanicActiveEvent()

    data object PullRefresh : MechanicActiveEvent()

    data object StartLoading: MechanicActiveEvent()

    data object EndLoading: MechanicActiveEvent()

    data object ShowSuccessDialog: MechanicActiveEvent()

    data object ShowFailureDialog: MechanicActiveEvent()

    data object CloseSuccessDialog: MechanicActiveEvent()

    data object CloseFailureDialog: MechanicActiveEvent()
}