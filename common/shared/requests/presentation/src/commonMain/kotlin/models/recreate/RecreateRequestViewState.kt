package models.recreate

import models.create.VehicleType

data class RecreateRequestViewState(
    val selectedVehicleType: VehicleType = VehicleType.Tractor,
    val numberVehicle:String="",
    val faultDescription:String="",
    val showSuccessDialog: Boolean=false,
    val showFailureDialog: Boolean=false,
    val trailerIsExpanded: Boolean = false,
    val tractorIsExpanded: Boolean = true,
    val imageIsExpanded: Boolean = false,
    val showFilePicker: Boolean = false,
    val requestId:Int=-1
)