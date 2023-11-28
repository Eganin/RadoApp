package models

sealed class ArchiveRequestsForMechanicItem {

    data class Success(val items: List<SmallArchiveRequestForMechanic>):ArchiveRequestsForMechanicItem()

    data class Error(val message:String): ArchiveRequestsForMechanicItem()
}