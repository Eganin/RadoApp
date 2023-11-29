package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FullRejectRequestResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("typeVehicle")
    val typeVehicle: String,
    @SerialName("numberVehicle")
    val numberVehicle: String,
    @SerialName("faultDescription")
    val faultDescription: String = "",
    @SerialName("driverId")
    val driverId: Int,
    @SerialName("images")
    val images: List<String>,
    @SerialName("videos")
    val videos:List<String>
)