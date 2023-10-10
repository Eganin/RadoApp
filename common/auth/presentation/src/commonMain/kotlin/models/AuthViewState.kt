package models

import androidx.compose.ui.geometry.Size

data class AuthViewState(
    val position: Position = Position.DRIVER,
    val fullName: String = "",
    val phone: String = "",
    val exposedMenuValue: String = "",
    val exposedMenuIsEnabled: Boolean = false,
    val exposedMenuSize: Size = Size.Zero,
    val exposedMenuSelectedIndex: Int = -1,
    val itemsExposedMenu: List<String> = listOf(
        Position.DRIVER.positionName,
        Position.MECHANIC.positionName,
        Position.OBSERVER.positionName
    )
)

enum class Position(
    val positionName: String
) {
    DRIVER("Водитель"),
    MECHANIC("Механик"),
    OBSERVER("Наблюдатель")
}
