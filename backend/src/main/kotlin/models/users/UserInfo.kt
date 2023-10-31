package models.users

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoRemote(
    val position: String,
    val fullName: String,
    val phone: String
)

@Serializable
data class LoginResponse(
    val userId: Int,
    val position: String,
    val fullName: String,
    val phone: String
)

enum class Position(
    val position: String
){
    DRIVER("Водитель"),
    MECHANIC("Механик"),
    OBSERVER("Наблюдатель")
}