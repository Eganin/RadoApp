package models

import kotlinx.serialization.Serializable

@Serializable
data class UserIdResponse(
    val userId: Int
)
