package org.company.rado.models.requests.active

import kotlinx.serialization.Serializable

@Serializable
data class SmallActiveRequestForMechanic(
    val id:Int,
    val typeVehicle: String,
    val numberVehicle:String,
    val datetime:String
)
