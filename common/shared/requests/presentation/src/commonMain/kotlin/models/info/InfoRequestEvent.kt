package models.info

sealed class InfoRequestEvent {

    data class UnconfirmedRequestGetInfo(val value: Int) : InfoRequestEvent()

    data class PhoneClick(val value: String) : InfoRequestEvent()
}