package models

sealed class UnconfirmedRequestsItem {

    data class Success(val items: List<SmallUnconfirmedRequestResponse>) : UnconfirmedRequestsItem()

    data class Error(val message: String) : UnconfirmedRequestsItem()
}
