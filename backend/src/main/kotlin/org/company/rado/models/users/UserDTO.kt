package org.company.rado.models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id:Int=0,
    val fullName: String,
    val phone: String
)
