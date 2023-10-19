package models

data class CreateRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessCreateRequestDialog: Boolean = false
)


enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = "Тягач"),
    Trailer(nameVehicleType = "Прицеп")
}