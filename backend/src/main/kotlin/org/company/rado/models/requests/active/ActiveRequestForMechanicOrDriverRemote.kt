package org.company.rado.models.requests.active

import kotlinx.serialization.Serializable

@Serializable
data class ActiveRequestForMechanicOrDriverRemote(
    val userId: Int,
    val date: String =""
)
