package models

sealed class UnconfirmedRequestInfoItem {

    data class Success(val requestInfo: FullUnconfirmedRequestResponse) :
        UnconfirmedRequestInfoItem()

    data class Error(val message: String) : UnconfirmedRequestInfoItem()
}
