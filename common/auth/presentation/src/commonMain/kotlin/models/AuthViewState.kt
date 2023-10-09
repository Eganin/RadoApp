package models

data class AuthViewState(
    val position: Position = Position.DRIVER,
    val fullName: String = "",
    val phone: String = ""
)

enum class Position(
    val positionName: String
) {
    DRIVER("Водитель"),
    MECHANIC("Механик"),
    OBSERVER("Наблюдатель")
}
