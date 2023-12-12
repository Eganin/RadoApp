package views.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.company.rado.core.MainRes
import theme.Theme

@Composable
fun PermissionDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    successOnClick: () -> Unit,
    firstText: String,
    secondText: String,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = firstText,
                    color = Theme.colors.primaryTextColor,
                    fontSize = 16.sp
                )

                Text(
                    text = secondText,
                    color = Theme.colors.secondaryTextColor,
                    fontSize = 16.sp
                )

                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { onExit.invoke() }) {
                        Text(
                            text = MainRes.string.dismiss_button_title,
                            fontSize = 12.sp,
                            color = Theme.colors.primaryTextColor
                        )
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            onExit.invoke()
                            successOnClick.invoke()
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