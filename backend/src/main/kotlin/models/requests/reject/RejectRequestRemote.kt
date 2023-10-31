package models.requests.reject

import kotlinx.serialization.Serializable

@Serializable
data class RejectRequestRemote(
    val requestId: Int,
    val mechanicId:Int,
    val commentMechanic: String
)
