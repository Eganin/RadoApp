package models

sealed class AuthEvent {

    data class PositionChanged(val value: Position): AuthEvent()

    data class FullNameChanged(val value : String): AuthEvent()

    data class PhoneChanged(val value : String): AuthEvent()

    data object RegisterClick : AuthEvent()
}