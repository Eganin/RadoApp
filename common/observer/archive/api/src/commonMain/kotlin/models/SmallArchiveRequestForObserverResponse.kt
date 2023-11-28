package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmallArchiveRequestForObserverResponse(
    @SerialName("id")
    val id:Int,
    @SerialName("datetime")
    val datetime: String,
    @SerialName("mechanicName")
    val mechanicName: String,
    @SerialName("statusRepair")
    val statusRepair: Boolean
)