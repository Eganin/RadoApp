package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallRejectRequestResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("numberVehicle")
    val numberVehicle:String,
    @SerialName("mechanicName")
    val mechanicName:String
)
