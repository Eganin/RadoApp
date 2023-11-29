package models

data class ActiveViewState(
    val selectedDate: String = "",
    val requestsForMechanic: List<SmallActiveRequestForMechanic> = emptyList(),
    val requestsForObserver: List<SmallActiveRequestForObserverResponse> = emptyList(),
    val errorTextForRequestList: String = "",
    val showInfoDialog: Boolean = false,
    val requestIdForInfo: Int = -1,
    val isLoading: Boolean = false,
    val showArchieveRequestSuccessDialog:Boolean=false,
    val showArchieveRequestFailureDialog:Boolean=false,
)
