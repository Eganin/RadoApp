package models

data class CreateRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessCreateRequestDialog: Boolean = false,
    val showFailureCreateRequestDialog: Boolean = false,
    val notVehicleNumber: Boolean = false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = true
)


enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = "Тягач"),
    Trailer(nameVehicleType = "Прицеп")
}