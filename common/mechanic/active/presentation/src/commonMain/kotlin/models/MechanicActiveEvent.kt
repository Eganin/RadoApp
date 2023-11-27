package models

sealed class MechanicActiveEvent {

    data class SelectedDateChanged(val value: String) : MechanicActiveEvent()

    data class OpenDialogInfoRequest(val requestId: Int) : MechanicActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String) : MechanicActiveEvent()

    data object CloseInfoDialog : MechanicActiveEvent()

    data object PullRefresh : MechanicActiveEvent()
}