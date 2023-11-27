package org.company.rado.models.requests.confirmation

import kotlinx.serialization.Serializable

@Serializable
data class ConfirmationRequestRemote(
    val requestId: Int,
    val time: String,
    val date:String,
    val mechanicId :Int,
    val streetRepair:String=""
)
