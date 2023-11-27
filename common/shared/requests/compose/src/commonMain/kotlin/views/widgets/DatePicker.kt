package views.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.company.rado.core.MainRes
import theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    confirmAction: (Long) -> Unit,
    exitAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = {
            exitAction.invoke()
        },
        confirmButton = {
            TextButton(onClick = {
                confirmAction.invoke(datePickerState.selectedDateMillis!!)
            }) {
                Text(
                    text = MainRes.string.ok_button_title,
                    fontSize = 12.sp,
                    color = Theme.colors.primaryTextColor
                )
            }
        },
        dismissButton = {
            TextButton(onClick = {
                exitAction.invoke()
            }) {
                Text(
                    text = MainRes.string.dismiss_button_title,
                    fontSize = 12.sp,
                    color = Theme.colors.primaryTextColor
                )
            }
        },
        modifier = modifier.padding(16.dp)
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}