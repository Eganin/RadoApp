package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForObserverResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("typeVehicle")
    val typeVehicle: String,
    @SerialName("numberVehicle")
    val numberVehicle:String,
    @SerialName("mechanicName")
    val mechanicName:String,
    @SerialName("datetime")
    val datetime:String
)

