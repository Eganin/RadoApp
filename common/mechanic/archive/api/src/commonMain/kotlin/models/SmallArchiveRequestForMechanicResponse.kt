package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallArchiveRequestForMechanic(
    @SerialName("id")
    val id:Int,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("numberVehicle")
    val numberVehicle: String,
    @SerialName("statusRepair")
    val statusRepair: Boolean
)
