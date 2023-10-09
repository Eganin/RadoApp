package models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val position: String,
    val fullName: String,
    val phone: String
)
