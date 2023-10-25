package models.info

import other.Position

sealed class InfoRequestEvent {

    data class RequestGetInfo(
        val requestId: Int,
        val infoForPosition: Position,
        val isActiveRequest: Boolean
    ) : InfoRequestEvent()

    data class PhoneClick(val phoneNumber: String) : InfoRequestEvent()

    data object ImageRepairExpandedChanged : InfoRequestEvent()
}