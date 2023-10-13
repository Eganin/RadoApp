package models

sealed class DriverActiveAction {

    data class ShowErrorSnackBar(val message: String): DriverActiveAction()

    data object OpenCreateRequestScreen: DriverActiveAction()
}