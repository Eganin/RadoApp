package models

sealed class ActiveRequestsForMechanicItem {

    data class Success(val items: List<SmallActiveRequestForMechanic>):ActiveRequestsForMechanicItem()

    data class Error(val message:String): ActiveRequestsForMechanicItem()
}