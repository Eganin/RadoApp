package widgets.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.Theme

@Composable
fun ActionButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Theme.colors.primaryAction),
        shape = RoundedCornerShape(size = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = Theme.colors.secondaryTextColor,
            modifier = Modifier.padding(8.dp)
        )
    }
}