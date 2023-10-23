package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestIdResponse(
    @SerialName("id")
    val id: Int
)
