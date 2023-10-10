package ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class KtorRegisterOrLoginRequest(
    val position: String,
    val fullName: String,
    val phone: String
)
