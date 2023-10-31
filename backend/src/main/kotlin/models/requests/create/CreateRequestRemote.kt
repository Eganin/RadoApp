package models.requests.create

import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestRemote(
    val driverUsername: String,
    val typeVehicle: String,
    val numberVehicle: String,
    val faultDescription: String = ""
)
