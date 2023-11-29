package models

data class RejectRequestViewState(
    val isLoading:Boolean=false,
    val showRecreateDialog:Boolean=false,
    val requests: List<SmallRejectRequestResponse> = emptyList(),
    val requestIdForInfo:Int=-1,
    val errorTextForRequestList:String=""
)
