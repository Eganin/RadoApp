package views.create

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarView(
    state: DatePickerState,
    modifier: Modifier = Modifier,
) {

    DatePicker(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(color = if (isSystemInDarkTheme()) Theme.colors.secondaryTextColor else Color.Transparent)
            .border(border = BorderStroke(1.dp, Theme.colors.primaryTextColor)),
        title = {},
        showModeToggle = false,
        colors = DatePickerDefaults.colors(selectedDayContainerColor = Theme.colors.highlightColor)
    )
}