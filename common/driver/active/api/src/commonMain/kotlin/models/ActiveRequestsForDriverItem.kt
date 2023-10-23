package models

sealed class ActiveRequestsForDriverItem {
    data class Success(val items: List<SmallActiveRequestForDriverResponse>) : ActiveRequestsForDriverItem()

    data class Error(val message: String) : ActiveRequestsForDriverItem()
}
