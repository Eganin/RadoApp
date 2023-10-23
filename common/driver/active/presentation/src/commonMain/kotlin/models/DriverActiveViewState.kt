package models

data class DriverActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForDriverResponse> = emptyList(),
    val unconfirmedRequests: List<SmallUnconfirmedRequestResponse> = emptyList(),
    val errorTextForRequestList : String ="",
    val showCreateDialog : Boolean = false,
    val showInfoDialog: Boolean = false,
    val requestIdForInfo :Int=-1
)