package org.company.rado.models.requests.unconfirmed

import kotlinx.serialization.Serializable
import org.company.rado.models.requests.StatusRequest

@Serializable
data class FullUnconfirmedRequest(
    val id: Int,
    val vehicleType: String,
    val vehicleNumber: String,
    val driverUsername: String,
    val driverPhone: String,
    val statusRequest: StatusRequest = StatusRequest.UNCONFIRMED,
    val faultDescription: String,
    val images: List<String>,
    val videos: List<String>
)
