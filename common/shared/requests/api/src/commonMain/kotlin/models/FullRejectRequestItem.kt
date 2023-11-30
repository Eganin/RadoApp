package models

sealed class FullRejectRequestItem {

    data class Success(val request: FullRejectRequestResponse) : FullRejectRequestItem()

    data class Error(val message: String="") : FullRejectRequestItem()
}