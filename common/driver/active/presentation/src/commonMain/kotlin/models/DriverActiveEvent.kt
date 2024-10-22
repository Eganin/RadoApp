package models

sealed class DriverActiveEvent {

    data class SelectedDateChanged(val value: String) : DriverActiveEvent()

    data object OpenDialogCreateRequest : DriverActiveEvent()

    data class OpenDialogRecreateForUnconfirmedRequest(val requestId: Int): DriverActiveEvent()

    data class OpenDialogInfoRequest(val requestId: Int, val isActiveRequest: Boolean) :
        DriverActiveEvent()

    data class ErrorTextForRequestListChanged(val value: String) : DriverActiveEvent()

    data object CloseCreateDialog : DriverActiveEvent()

    data object CloseInfoDialog : DriverActiveEvent()

    data object CloseRecreateDialog: DriverActiveEvent()

    data object PullRefresh : DriverActiveEvent()
}