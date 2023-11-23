package models

data class MechanicRequestsViewState(
    val unconfirmedRequests: List<SmallUnconfirmedRequestResponse> = emptyList(),
    val errorTextForRequestList: String = "",
    val showInfoDialog: Boolean = false,
    val showSuccessDialog:Boolean=false,
    val showFailureDialog:Boolean=false,
    val showRejectDialog:Boolean=false,
    val reopenDialog: Boolean = false,
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val requestsIdForInfo: Int = -1,
    val datetime: String = "",
    val datetimeForServer: Pair<String, String> = Pair("", ""),
    val date: Long = 0,
    val isLoading: Boolean = false,
    val mechanicComment:String=""
)