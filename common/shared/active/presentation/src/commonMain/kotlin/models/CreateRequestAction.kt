package models

sealed class CreateRequestAction {

    data class ShowErrorSnackBar(val message:String): CreateRequestAction()
}