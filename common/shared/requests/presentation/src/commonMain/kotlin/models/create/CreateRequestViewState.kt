package models.create

import MediaTypePresentation
import org.company.rado.core.MainRes

data class CreateRequestViewState(
    val isSelectedTractor: Boolean = false,
    val isSelectedTrailer: Boolean = false,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessCreateRequestDialog: Boolean = false,
    val showFailureCreateRequestDialog: Boolean = false,
    val notVehicleNumber: Boolean = false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = false,
    val imageIsExpanded: Boolean = false,
    val showFilePicker: Boolean = false,
    val resources: List<Triple<String, Boolean, ByteArray>> = emptyList(),
    val isLoading: Boolean = false,
    val notChooseVehicle:Boolean=false,
    val arrivalDate:String="",
    val showDatePicker:Boolean=false,
    val media: MutableList<MediaTypePresentation> = mutableListOf()
)


enum class VehicleType(val nameVehicleType: String) {
    Tractor(nameVehicleType = MainRes.string.tractor_enum_value),
    Trailer(nameVehicleType = MainRes.string.trailer_enum_value)
}

fun getVehicleType(isSelectedTractor: Boolean, isSelectedTrailer: Boolean): String {
    val vehicleTypeTractor = if (isSelectedTractor) VehicleType.Tractor.nameVehicleType else ""
    val vehicleTypeTrailer = if (isSelectedTrailer) VehicleType.Trailer.nameVehicleType else ""
    val vehicles = mutableListOf(vehicleTypeTractor,vehicleTypeTrailer).filter { it.isNotEmpty() }
    return vehicles.joinToString(separator = "-")
}

fun String.toVehicleType(): Pair<Boolean,Boolean> {
    val isTractor = this.contains(VehicleType.Tractor.nameVehicleType)
    val isTrailer = this.contains(VehicleType.Trailer.nameVehicleType)
    return Pair(first=isTractor,second = isTrailer)
}