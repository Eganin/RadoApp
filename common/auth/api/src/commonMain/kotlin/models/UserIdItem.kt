package models

sealed class UserIdItem {

    data class Success(val userId: Int): UserIdItem()

    data class Error(val message: String): UserIdItem()
}