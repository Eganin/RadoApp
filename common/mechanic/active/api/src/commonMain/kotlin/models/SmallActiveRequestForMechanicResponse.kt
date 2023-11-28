package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForMechanic(
    @SerialName("id")
    val id: Int,
    @SerialName("typeVehicle")
    val typeVehicle: String,
    @SerialName("numberVehicle")
    val numberVehicle: String,
    @SerialName("datetime")
    val datetime: String
)
