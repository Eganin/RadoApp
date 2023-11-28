package models

data class MechanicArchiveViewState(
    val requests: List<SmallArchiveRequestForMechanic> = emptyList(),
    val errorTextForRequestList:String="",
    val showInfoDialog:Boolean=false,
    val requestIdForInfo:Int=-1,
    val isLoading:Boolean=false
)
