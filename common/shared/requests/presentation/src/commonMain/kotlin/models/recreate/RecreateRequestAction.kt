package models.recreate

sealed class RecreateRequestAction {
    data object CloseReCreateRequestAlertDialog : RecreateRequestAction()
}