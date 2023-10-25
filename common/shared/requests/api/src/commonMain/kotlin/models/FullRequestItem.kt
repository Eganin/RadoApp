package models

sealed class FullRequestItem {

    data class Success(val request: FullRequestResponse) : FullRequestItem()

    data class Error(val message: String) : FullRequestItem()
}
