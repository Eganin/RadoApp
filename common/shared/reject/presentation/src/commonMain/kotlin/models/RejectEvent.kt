package models

sealed class RejectEvent {

    data class OpenRecreateDialog(val requestId: Int) : RejectEvent()

    data object CloseRecreateDialog : RejectEvent()

    data class ErrorTextForRequestListChanged(val message: String) : RejectEvent()

    data object PullRefresh : RejectEvent()

    data object StartLoading : RejectEvent()

    data object EndLoading : RejectEvent()
}