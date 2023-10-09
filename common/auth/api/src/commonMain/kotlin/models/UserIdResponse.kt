package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserIdResponse(
    @SerialName("userId")
    val userId: Int
)
