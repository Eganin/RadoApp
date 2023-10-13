package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForDriverResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("mechanicName")
    val mechanicName:String,
    @SerialName("datetime")
    val datetime:String
)
