package models.requests.active

import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForDriver(
    val id:Int,
    val mechanicName:String,
    val datetime:String
)
