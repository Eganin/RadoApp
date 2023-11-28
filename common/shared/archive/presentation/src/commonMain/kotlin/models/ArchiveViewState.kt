package models

data class ArchiveViewState(
    val requestsForMechanic: List<SmallArchiveRequestForMechanic> = emptyList(),
    val requestsForDriver: List<SmallArchiveRequestForDriverResponse> = emptyList(),
    val requestsForObserver:List<SmallArchiveRequestForObserverResponse> = emptyList(),
    val errorTextForRequestList:String="",
    val showInfoDialog:Boolean=false,
    val requestIdForInfo:Int=-1,
    val isLoading:Boolean=false
)
