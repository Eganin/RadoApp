package models

data class MechanicRequestsViewState(
    val unconfirmedRequests: List<SmallUnconfirmedRequestResponse> = emptyList(),
    val errorTextForRequestList : String ="",
    val showInfoDialog:Boolean = false,
    val requestsIdForInfo: Int=-1,
    val datetime: String ="",
    val rejectDescription:String="",
    val isLoading: Boolean = false
)