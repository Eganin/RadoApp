package org.company.rado.models.requests

import kotlinx.serialization.Serializable

@Serializable
data class RequestDTO(
    val id:Int =0,
    val vehicleId: Int,
    val driverId: Int,
    val statusRequest: StatusRequest = StatusRequest.UNCONFIRMED,
    val faultDescription: String,
    val mechanicId: Int? = null,
    val date: String? = null,
    val time: String?=null,
    val statusRepair: Boolean? = null,
    val commentMechanic: String? = null,
    val arrivalDate:String?=null
)

enum class StatusRequest {
    UNCONFIRMED,
    ACTIVE,
    ARCHIVE,
    REJECT
}
