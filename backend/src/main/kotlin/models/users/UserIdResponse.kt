package models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserIdResponse(
    val userId: Int
)
