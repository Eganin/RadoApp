package models

sealed class MechanicRequestsAction {

    data class ShowErrorSnackBar(val message: String) : MechanicRequestsAction()
}