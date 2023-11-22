package views.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.company.rado.core.MainRes
import theme.Theme

@Composable
internal fun AlertDialogTopBar(modifier: Modifier = Modifier,onBackClick: () -> Unit) {
    Row(modifier = modifier.clickable {
        onBackClick.invoke()
    }) {
        Icon(imageVector = FeatherIcons.ArrowLeft, contentDescription = null)

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = MainRes.string.back_title,
            fontSize = 18.sp,
            color = Theme.colors.primaryTextColor,
            textAlign = TextAlign.Center
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}