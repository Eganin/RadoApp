package models

sealed class ActiveRequestsForObserverItem {

    data class Success(val items: List<SmallActiveRequestForObserverResponse>): ActiveRequestsForObserverItem()

    data class Error(val message: String): ActiveRequestsForObserverItem()
}