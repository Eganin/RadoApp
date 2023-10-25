package models

sealed class MechanicRequestsAction {

    data class ShowSnackBar(val message: String) : MechanicRequestsAction()
}