package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecreateRequest(
    @SerialName("driverId")
    val driverId: Int,
    @SerialName("typeVehicle")
    val typeVehicle: String,
    @SerialName("numberVehicle")
    val numberVehicle: String,
    @SerialName("oldTypeVehicle")
    val oldTypeVehicle: String,
    @SerialName("oldNumberVehicle")
    val oldNumberVehicle: String,
    @SerialName("faultDescription")
    val faultDescription: String = "",
    @SerialName("arrivalDate")
    val arrivalDate:String
)