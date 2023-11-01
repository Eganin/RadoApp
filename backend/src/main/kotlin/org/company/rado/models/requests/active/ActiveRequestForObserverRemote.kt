package org.company.rado.models.requests.active

import kotlinx.serialization.Serializable

@Serializable
data class ActiveRequestForObserverRemote(
    val date: String=""
)
