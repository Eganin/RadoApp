package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KtorActiveRequestForObserver(
    @SerialName("date")
    val date: String
)
