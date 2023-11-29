package models

sealed class ActiveEvent {

    data class SelectedDateChanged(val value: String) : ActiveEvent()

    data class OpenDialogInfoRequest(val requestId: Int) : ActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String) : ActiveEvent()

    data object ArchieveRequest:ActiveEvent()

    data object CloseInfoDialog : ActiveEvent()

    data object PullRefresh : ActiveEvent()

    data object StartLoading: ActiveEvent()

    data object EndLoading: ActiveEvent()

    data object ShowSuccessDialog: ActiveEvent()

    data object ShowFailureDialog: ActiveEvent()

    data object CloseSuccessDialog: ActiveEvent()

    data object CloseFailureDialog: ActiveEvent()
}