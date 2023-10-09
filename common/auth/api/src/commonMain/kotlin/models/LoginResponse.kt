package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("position")
    val position: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("phone")
    val phone: String
)
