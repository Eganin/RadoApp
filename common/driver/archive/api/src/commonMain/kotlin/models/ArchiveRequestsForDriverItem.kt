package models

sealed class ArchiveRequestsForDriverItem {

    data class Success(val items: List<SmallArchiveRequestForDriverResponse>): ArchiveRequestsForDriverItem()

    data class Error(val message:String):ArchiveRequestsForDriverItem()
}