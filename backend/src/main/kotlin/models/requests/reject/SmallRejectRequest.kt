package models.requests.reject

import kotlinx.serialization.Serializable

@Serializable
data class SmallRejectRequest(
    val id:Int,
    val numberVehicle:String,
    val mechanicName:String
)
