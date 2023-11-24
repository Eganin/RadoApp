package views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.company.rado.core.MainRes
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import widgets.common.TextStickyHeader

@Composable
internal fun MechanicRejectDialog(
    mechanicComment: String,
    onSend: () -> Unit,
    onExit: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop

    Dialog(
        onDismissRequest = onExit,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp)
            ) {
                TextStickyHeader(textTitle = MainRes.string.reject_request_title)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Theme.colors.primaryTextColor,
                        unfocusedTextColor = Theme.colors.primaryTextColor,
                        disabledTextColor = Theme.colors.primaryTextColor,
                    ),
                    value = mechanicComment,
                    onValueChange = onValueChange,
                    label = {
                        Text(
                            text = MainRes.string.reject_request_label,
                            color = Theme.colors.primaryTextColor,
                            fontSize = if (isLargePlatform) 16.sp else 8.sp
                        )
                    },
                    modifier = modifier.fillMaxWidth(),
                    maxLines = 10
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
                            onSend.invoke()
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