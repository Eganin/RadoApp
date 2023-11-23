package models.recreate

import models.create.VehicleType

data class RecreateRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val oldSelectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val notVehicleNumber:Boolean=false,
    val oldNumberVehicle: String = "",
    val faultDescription: String = "",
    val showSuccessDialog: Boolean = false,
    val showFailureDialog: Boolean = false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = true,
    val imageIsExpanded: Boolean = false,
    val showFilePicker: Boolean = false,
    val requestId: Int = -1,
    val isLoading: Boolean = false,
    val resources: List<Triple<String, Boolean, ByteArray>> = emptyList(),
    val images: List<String> = emptyList(),
    val videos: List<String> = emptyList()
)