package org.company.rado.models.requests.reject

import kotlinx.serialization.Serializable

@Serializable
data class FullRejectRequest(
    val id: Int,
    val typeVehicle: String,
    val numberVehicle: String,
    val faultDescription: String = "",
    val driverId: Int,
    val images: List<String>
)
