package org.company.rado.models.requests.archive

import kotlinx.serialization.Serializable

@Serializable
data class SmallArchiveRequestForMechanic(
    val id:Int,
    val datetime: String,
    val numberVehicle: String,
    val statusRepair: Boolean
)
