package models

sealed class CreateRequestAction {
    data object CloseCreateRequestAlertDialog : CreateRequestAction()
}