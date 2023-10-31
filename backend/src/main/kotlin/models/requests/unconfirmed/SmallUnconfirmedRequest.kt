package models.requests.unconfirmed

import kotlinx.serialization.Serializable
import models.requests.StatusRequest

@Serializable
data class SmallUnconfirmedRequest(
    val id: Int,
    val vehicleType: String,
    val vehicleNumber: String,
    val statusRequest: StatusRequest = StatusRequest.UNCONFIRMED
)