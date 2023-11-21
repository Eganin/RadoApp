package models.recreate

import models.create.VehicleType

data class RecreateRequestViewState(
    val recreateSelectedVehicleType: VehicleType = VehicleType.Tractor,
    val recreateNumberVehicle:String="",
    val recreateFaultDescription:String="",
)