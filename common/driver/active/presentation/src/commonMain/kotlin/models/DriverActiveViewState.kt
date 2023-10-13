package models

data class DriverActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForDriverResponse> = emptyList(),
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val errorTextForRequestList : String ="",
    val showCreateDialog : Boolean = false,
    val showSuccessCreateRequestDialog : Boolean= false
)

enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = "Тягач"),
    Trailer(nameVehicleType = "Прицеп")
}
