package models.vehicles

import kotlinx.serialization.Serializable

@Serializable
data class VehicleDTO(
    val typeVehicle: String,
    val numberVehicle:String
)