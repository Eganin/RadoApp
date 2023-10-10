package models

import androidx.compose.ui.geometry.Size

data class AuthViewState(
    val position: Position = Position.DRIVER,
    val firstName: String = "",
    val secondName: String = "",
    val thirdName: String = "",
    val phone: String = "",
    val exposedMenuValue: String = Position.DRIVER.positionName,
    val exposedMenuIsEnabled: Boolean = false,
    val exposedMenuSize: Size = Size.Zero,
    val exposedMenuSelectedIndex: Int = 0,
    val itemsExposedMenu: List<Position> = listOf(
        Position.DRIVER,
        Position.MECHANIC,
        Position.OBSERVER
    )
)

enum class Position(
    val positionName: String
) {
    DRIVER("Водитель"),
    MECHANIC("Механик"),
    OBSERVER("Наблюдатель")
}

fun String.fromPositionNameToPosition(): Position {
    return when (this) {
        Position.DRIVER.positionName -> Position.DRIVER
        Position.MECHANIC.positionName -> Position.MECHANIC
        else -> Position.OBSERVER
    }
}
