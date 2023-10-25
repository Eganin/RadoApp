package models

import androidx.compose.ui.geometry.Size
import other.Position

data class AuthViewState(
    val position: Position = Position.DRIVER,
    val firstName: String = "",
    val secondName: String = "",
    val thirdName: String = "",
    val phone: String = "",
    val isFirstSignIn : Boolean=true,
    val exposedMenuValue: String = Position.DRIVER.positionName,
    val exposedMenuIsEnabled: Boolean = false,
    val exposedMenuSize: Size = Size.Zero,
    val exposedMenuSelectedIndex: Int = 0,
    val itemsExposedMenu: List<Position> = listOf(
        Position.DRIVER,
        Position.MECHANIC,
        Position.OBSERVER
    ),
    val isLoading : Boolean = false
)

fun String.fromPositionNameToPosition(): Position {
    return when (this) {
        Position.DRIVER.positionName -> Position.DRIVER
        Position.MECHANIC.positionName -> Position.MECHANIC
        else -> Position.OBSERVER
    }
}
