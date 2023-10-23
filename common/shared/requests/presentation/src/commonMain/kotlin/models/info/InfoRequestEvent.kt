package models.info

sealed class InfoRequestEvent {

    data class UnconfirmedRequestGetInfo(val requestId: Int) : InfoRequestEvent()

    data class ActiveRequestGetInfo(val requestId:Int): InfoRequestEvent()

    data class PhoneClick(val phoneNumber: String) : InfoRequestEvent()

    data object ImageRepairExpandedChanged: InfoRequestEvent()
}