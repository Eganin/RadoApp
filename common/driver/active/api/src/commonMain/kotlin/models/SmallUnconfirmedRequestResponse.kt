package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import other.StatusRequest

@Serializable
data class SmallUnconfirmedRequestResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("vehicleType")
    val vehicleType: String,
    @SerialName("vehicleNumber")
    val vehicleNumber: String,
    @SerialName("statusRequest")
    val statusRequest: StatusRequest
)