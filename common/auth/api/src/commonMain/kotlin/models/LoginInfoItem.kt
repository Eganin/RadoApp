package models

sealed class LoginInfoItem {

    data class Success(val userId: Int, val position: String, val fullName: String, val phone: String) : LoginInfoItem()

    data class Error(val message: String) : LoginInfoItem()
}