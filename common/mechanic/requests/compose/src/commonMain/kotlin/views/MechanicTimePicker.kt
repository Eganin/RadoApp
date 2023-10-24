package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.github.aakira.napier.log
import org.company.rado.core.MainRes
import theme.Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MechanicTimePicker(
    confirmAction: (Pair<Int, Int>) -> Unit,
    exitAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timePickerState = rememberTimePickerState()
    Dialog(
        onDismissRequest = exitAction,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // time picker
                TimePicker(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { exitAction.invoke() }) {
                        Text(
                            text = MainRes.string.dismiss_button_title,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor
                        )
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            val answer =
                                Pair(first = timePickerState.hour, second = timePickerState.minute)
                            log(tag = "TIME") { answer.toString() }
                            confirmAction.invoke(answer)
                        }
                    ) {
                        Text(
                            text = MainRes.string.ok_button_title,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor
                        )
                    }
                }
            }
        }
    }
}