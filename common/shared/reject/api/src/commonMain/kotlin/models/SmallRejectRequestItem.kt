package models

sealed class SmallRejectRequestItem {

    data class Success(val items:SmallRejectRequestResponse):SmallRejectRequestItem()

    data class Error(val message:String):SmallRejectRequestItem()
}