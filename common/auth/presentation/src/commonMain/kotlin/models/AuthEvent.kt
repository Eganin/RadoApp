package models

import androidx.compose.ui.geometry.Size
import other.Position

sealed class AuthEvent {

    data class PositionChanged(val value: Position) : AuthEvent()

    data class FirstNameChanged(val value: String) : AuthEvent()

    data class SecondNameChanged(val value: String) : AuthEvent()

    data class ThirdNameChanged(val value: String) : AuthEvent()

    data class PhoneChanged(val value: String) : AuthEvent()

    data class ExposedMenuEnableChanged(val value: Boolean) : AuthEvent()

    data class ExposedMenuSizeChanged(val value: Size) : AuthEvent()

    data class ExposedMenuIndexChanged(val value : Int): AuthEvent()

    data class IsFirstSignUpChanged(val value: Boolean): AuthEvent()

    data object RegisterClick : AuthEvent()

    data object StartLoading : AuthEvent()

    data object EndLoading : AuthEvent()
}