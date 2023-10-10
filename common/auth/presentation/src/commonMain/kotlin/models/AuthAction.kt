package models

sealed class AuthAction {

    data class ShowErrorSnackBar(val message: String): AuthAction()

    data object OpenMainFlow: AuthAction()
}