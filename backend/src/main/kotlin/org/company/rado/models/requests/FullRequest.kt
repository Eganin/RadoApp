package org.company.rado.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class FullRequest(
    val id: Int = 0,
    val vehicleNumber: String,
    val vehicleType: String,
    val driverName: String,
    val driverPhone: String,
    val statusRequest: StatusRequest,
    val faultDescription: String,
    val mechanicName: String,
    val mechanicPhone: String,
    val date: String,
    val time: String,
    val statusRepair: Boolean,
    val commentMechanic: String? = null,
    val images: List<String>,
    val videos: List<String>,
    val arrivalDate:String="",
    val streetRepair:String=""
)
