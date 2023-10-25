package views.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.AlignJustify
import org.company.rado.core.MainRes
import theme.Theme

@Composable
fun RequestCells(
    firstText: String,
    secondText: String,
    isReissueRequest: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onReissueRequest: () -> Unit = {}
) {
    Row(modifier = modifier.clickable { onClick.invoke() }.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = firstText,
                color = Theme.colors.primaryTextColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = secondText,
                color = Theme.colors.secondaryTextColor,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
        if (isReissueRequest) {
            TextButton(
                onClick = onReissueRequest,
                colors = ButtonDefaults.textButtonColors(contentColor = Theme.colors.highlightColor)
            ) {
                Text(
                    text = MainRes.string.reissue_request_title,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Icon(
                imageVector = FeatherIcons.AlignJustify,
                contentDescription = null,
                tint = Theme.colors.primaryTextColor
            )
        }
    }
}