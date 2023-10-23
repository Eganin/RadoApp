package models

sealed class UnconfirmedRequestsForDriverItem {

    data class Success(val items: List<SmallUnconfirmedRequestResponse>) : UnconfirmedRequestsForDriverItem()

    data class Error(val message: String) : UnconfirmedRequestsForDriverItem()
}
