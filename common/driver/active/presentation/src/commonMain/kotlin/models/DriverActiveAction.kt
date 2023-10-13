package models

sealed class DriverActiveAction {

    data class ShowErrorSnackBar(val message: String) : DriverActiveAction()

    data object ShowSuccessCreateRequestDialog : DriverActiveAction()

    data object OpenCreateRequestDialog : DriverActiveAction()
}