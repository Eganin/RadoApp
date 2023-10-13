package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorCreateRequest(
    @SerialName("driverUsername")
    val driverUsername: String,
    @SerialName("typeVehicle")
    val typeVehicle: String,
    @SerialName("numberVehicle")
    val numberVehicle: String,
    @SerialName("faultDescription")
    val faultDescription: String
)
