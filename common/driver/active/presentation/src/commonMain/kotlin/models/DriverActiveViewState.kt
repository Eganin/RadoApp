package models

data class DriverActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForDriverResponse> = emptyList(),
    val unconfirmedRequests: List<SmallUnconfirmedRequestResponse> = emptyList(),
    val errorTextForRequestList : String ="",
    val showCreateDialog : Boolean = false,
    val showRecreateDialog: Boolean=false,
    val showRecreateDialogForActiveRequest:Boolean=false,
    val showInfoDialog: Boolean = false,
    val isActiveDialog:Boolean=false,
    val requestIdForInfo :Int=-1,
    val isLoadingUnconfirmedRequests: Boolean=false,
    val isLoadingActiveRequests: Boolean=false
)