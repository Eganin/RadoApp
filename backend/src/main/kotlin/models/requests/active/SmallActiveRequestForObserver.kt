package models.requests.active

import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForObserver(
    val id:Int,
    val typeVehicle: String,
    val numberVehicle:String,
    val mechanicName:String,
    val datetime:String
)
