package models.create

sealed class CreateRequestAction {
    data object CloseCreateRequestAlertDialog : CreateRequestAction()
}