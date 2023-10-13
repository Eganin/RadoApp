package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorActiveRequest(
    @SerialName("userId")
    val userId: Int,
    @SerialName("date")
    val date: String
)