package ktor.models

import kotlinx.serialization.Serializable

@Serializable
data class KtorUnconfirmedRequest(
    val userId: Int
)
