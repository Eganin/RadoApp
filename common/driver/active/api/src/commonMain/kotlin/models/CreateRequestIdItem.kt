package models

sealed class CreateRequestIdItem {

    data class Success(val requestId: Int) : CreateRequestIdItem()

    data class Error(val message: String) : CreateRequestIdItem()
}