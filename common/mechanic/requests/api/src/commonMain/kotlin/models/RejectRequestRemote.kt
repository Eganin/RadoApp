package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RejectRequestRemote(
    @SerialName("requestId")
    val requestId: Int,
    @SerialName("mechanicId")
    val mechanicId:Int,
    @SerialName("commentMechanic")
    val commentMechanic: String
)