package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import other.StatusRequest

@Serializable
data class FullUnconfirmedRequestResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("vehicleType")
    val vehicleType: String,
    @SerialName("vehicleNumber")
    val vehicleNumber: String,
    @SerialName("driverUsername")
    val driverUsername: String,
    @SerialName("driverPhone")
    val driverPhone: String,
    @SerialName("statusRequest")
    val statusRequest: StatusRequest,
    @SerialName("faultDescription")
    val faultDescription: String,
    @SerialName("images")
    val images: List<String>,
    @SerialName("videos")
    val videos: List<String>
)