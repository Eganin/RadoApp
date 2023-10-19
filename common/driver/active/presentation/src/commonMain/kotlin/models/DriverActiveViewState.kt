package models

data class DriverActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForDriverResponse> = emptyList(),
    val errorTextForRequestList : String ="",
    val showCreateDialog : Boolean = false
)