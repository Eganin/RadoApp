package models

data class MechanicActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForMechanic> = emptyList(),
    val errorTextForRequestList: String = "",
    val showInfoDialog: Boolean = false,
    val requestIdForInfo: Int = -1,
    val isLoading: Boolean = false,
    val showArchieveRequestSuccessDialog:Boolean=false,
    val showArchieveRequestFailureDialog:Boolean=false,
)
