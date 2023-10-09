package theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class RadoColors(
    val primaryBackground: Color,
    val primaryAction: Color,
    val primaryTextColor: Color,
    val hintTextColor: Color,
    val highlightColor: Color,
    val secondaryTextColor: Color,
    val thirdTextColor: Color,
    val tagColor: Color,
    val tagTextColor: Color,
    val bottomBarColor:Color,
    val calendarColor: Color,
    val bottomCellsColor:Color
)

val lightPalette = RadoColors(
    primaryBackground = Color(0xFFFFFFFF),
    primaryAction = Color(0xFFF8D247),
    primaryTextColor = Color(0xFF050B18),
    hintTextColor = Color(0xFF333232),
    highlightColor = Color(0xFF6200EE),
    secondaryTextColor = Color(0xFF000000),
    thirdTextColor = Color(0xFF0047FF),
    tagColor = Color(0xFF6200EE),
    tagTextColor = Color(0xFFFFFFFF),
    bottomBarColor = Color(0xFFFFFFFF),
    calendarColor = Color(0xFFFFFFFF),
    bottomCellsColor = Color(0xFFE0E0E0)
)

val darkPalette = RadoColors(
    primaryBackground = Color(0xFF282323),
    primaryAction = Color(0xFFF8D247),
    primaryTextColor = Color(0xFFFFFFFF),
    hintTextColor = Color(0xFFFFE8E8),
    highlightColor = Color(0xFF6200EE),
    secondaryTextColor = Color(0xFFFFE8E8),
    thirdTextColor = Color(0xFF0047FF),
    tagColor = Color(0xFF6200EE),
    tagTextColor = Color(0xFFFFFFFF),
    bottomBarColor = Color(0xFF000000),
    calendarColor = Color(0xFF000000),
    bottomCellsColor = Color(0xFFFFE8E8)
)

val LocalColorProvider = staticCompositionLocalOf<RadoColors> {
    error("No default implementation theme")
}