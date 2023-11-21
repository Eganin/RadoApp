package models.recreate

import models.create.VehicleType

data class RecreateInfoModel(
    val requestId:Int,
    val selectedVehicleType: VehicleType,
    val numberVehicle: String,
    val faultDescription: String,
    val images:List<String>,
    val videos:List<String>
)
