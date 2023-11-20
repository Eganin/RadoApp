package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import other.StatusRequest

@Serializable
data class FullRequestResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("vehicleNumber")
    val vehicleNumber: String,
    @SerialName("vehicleType")
    val vehicleType: String,
    @SerialName("driverName")
    val driverName: String,
    @SerialName("driverPhone")
    val driverPhone: String,
    @SerialName("statusRequest")
    val statusRequest: StatusRequest,
    @SerialName("faultDescription")
    val faultDescription: String,
    @SerialName("mechanicName")
    val mechanicName: String,
    @SerialName("mechanicPhone")
    val mechanicPhone: String,
    @SerialName("date")
    val date: String,
    @SerialName("time")
    val time: String,
    @SerialName("statusRepair")
    val statusRepair: Boolean,
    @SerialName("commentMechanic")
    val commentMechanic: String?,
    @SerialName("images")
    val images: List<String>,
    @SerialName("videos")
    val videos: List<String>
)
