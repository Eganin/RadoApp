package models

import other.Position

sealed class AuthAction {

    data class ShowErrorSnackBar(val message: String): AuthAction()

    data class OpenMainFlow(val position: Position): AuthAction()
}