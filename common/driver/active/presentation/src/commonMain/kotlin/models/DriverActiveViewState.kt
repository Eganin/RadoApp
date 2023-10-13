package models

data class DriverActiveViewState(
    val selectedDate: String = "",
    val requests: List<SmallActiveRequestForDriverResponse> = emptyList(),
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = ""
)

enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = "Тягач"),
    Trailer(nameVehicleType = "Прицеп")
}
