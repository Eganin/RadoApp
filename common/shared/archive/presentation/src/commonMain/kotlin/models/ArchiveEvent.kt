package models

sealed class ArchiveEvent {

    data class OpenDialogInfoRequest(val requestId:Int):ArchiveEvent()

    data object CloseInfoDialog:ArchiveEvent()

    data class ErrorTextForRequestListChanged(val message:String):ArchiveEvent()

    data object PullRefresh: ArchiveEvent()

    data object StartLoading: ArchiveEvent()

    data object EndLoading: ArchiveEvent()
}