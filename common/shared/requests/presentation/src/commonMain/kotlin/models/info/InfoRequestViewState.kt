package models.info

import models.create.VehicleType

data class InfoRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val images: List<String> = emptyList(),
    val mechanicPhone: String = "",
    val driverPhone: String = "",
    val datetime: String = "",
    val errorTitleMessage: String = "",
    val imageIsExpanded: Boolean = false,
    val isLoading: Boolean = false
)
