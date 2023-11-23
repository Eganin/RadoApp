package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecreateRequestResponse(
    @SerialName("id")
    val id: Int
)