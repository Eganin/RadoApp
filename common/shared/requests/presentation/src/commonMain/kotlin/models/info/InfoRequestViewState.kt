package models.info

import models.create.VehicleType
import other.Position
import other.StatusRequest

data class InfoRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle: String = "",
    val faultDescription: String = "",
    val images: List<String> = emptyList(),
    val mechanicPhone: String = "",
    val mechanicName: String = "",
    val driverPhone: String = "",
    val driverName: String = "",
    val statusRequest: StatusRequest = StatusRequest.UNCONFIRMED,
    val datetime: String = "",
    val statusRepair: Boolean = false,
    val commentMechanic: String = "",
    val errorTitleMessage: String = "",
    val imageIsExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val infoForPosition: Position=Position.DRIVER,
    val isActiveRequest: Boolean=false,
)
