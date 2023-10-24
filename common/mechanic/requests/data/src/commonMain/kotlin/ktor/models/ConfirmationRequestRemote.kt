package ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRequestRemote(
    @SerialName("requestId")
    val requestId: Int,
    @SerialName("time")
    val time: String,
    @SerialName("date")
    val date: String,
    @SerialName("mechanicId")
    val mechanicId: Int
)
