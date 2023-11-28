package models

sealed class ArchiveRequestsForObserverItem {

    data class Success(val items: List<SmallArchiveRequestForObserverResponse>) :
        ArchiveRequestsForObserverItem()

    data class Error(val message: String) : ArchiveRequestsForObserverItem()
}