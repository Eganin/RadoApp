package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorRegisterOrLoginRequest(
    @SerialName("position")
    val position: String,
    @SerialName("fullName")
    val fullName: String,
    @SerialName("phone")
    val phone: String
)
