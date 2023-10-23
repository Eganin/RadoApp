package models.info

sealed class InfoRequestAction {

    data object CloseDialog : InfoRequestAction()

    data object RejectDialog : InfoRequestAction()

    data object ChooseDateTime : InfoRequestAction()
}