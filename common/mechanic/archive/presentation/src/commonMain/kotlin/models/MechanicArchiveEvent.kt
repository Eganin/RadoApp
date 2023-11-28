package models

sealed class MechanicArchiveEvent {

    data class OpenDialogInfoRequest(val requestId:Int):MechanicArchiveEvent()

    data object CloseInfoDialog:MechanicArchiveEvent()

    data class ErrorTextForRequestListChanged(val message:String):MechanicArchiveEvent()

    data object PullRefresh: MechanicArchiveEvent()

    data object StartLoading: MechanicArchiveEvent()

    data object EndLoading: MechanicArchiveEvent()
}