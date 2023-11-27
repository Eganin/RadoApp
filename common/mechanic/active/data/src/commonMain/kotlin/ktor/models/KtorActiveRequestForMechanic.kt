package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorActiveRequestForMechanic(
    @SerialName("userId")
    val userId: Int,
    @SerialName("date")
    val date: String
)