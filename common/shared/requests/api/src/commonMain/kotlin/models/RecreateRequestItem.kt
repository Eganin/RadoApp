package models

sealed class RecreateRequestItem {

    data class Success(val response: RecreateRequestResponse): RecreateRequestItem()

    data class Error(val message:String=""):RecreateRequestItem()
}