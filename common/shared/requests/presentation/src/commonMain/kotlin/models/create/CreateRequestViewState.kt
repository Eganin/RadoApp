package models.create

import org.company.rado.core.MainRes

data class CreateRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessCreateRequestDialog: Boolean = false,
    val showFailureCreateRequestDialog: Boolean = false,
    val notVehicleNumber: Boolean = false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = true,
    val imageIsExpanded: Boolean = false,
    val showFilePicker: Boolean = false,
    val resources: List<Triple<String,Boolean, ByteArray>> = emptyList(),
    val isLoading:Boolean = false
)


enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = MainRes.string.tractor_enum_value),
    Trailer(nameVehicleType = MainRes.string.trailer_enum_value)
}

fun String.toVehicleType(): VehicleType {
    return when (this) {
        MainRes.string.tractor_enum_value -> VehicleType.Tractor
        else -> VehicleType.Trailer
    }
}