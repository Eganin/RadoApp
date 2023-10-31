package models.requests.archive

import kotlinx.serialization.Serializable

@Serializable
data class SmallArchiveRequest(
    val id:Int,
    val datetime: String,
    val mechanicName: String,
    val statusRepair: Boolean
)
