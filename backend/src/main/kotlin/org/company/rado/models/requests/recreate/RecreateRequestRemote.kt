package org.company.rado.models.requests.recreate

import kotlinx.serialization.Serializable

@Serializable
data class RecreateRequestRemote(
    val driverId: Int,
    val typeVehicle: String,
    val numberVehicle: String,
    val oldTypeVehicle: String,
    val oldNumberVehicle: String,
    val faultDescription: String = "",
    val arrivalDate:String=""
)
