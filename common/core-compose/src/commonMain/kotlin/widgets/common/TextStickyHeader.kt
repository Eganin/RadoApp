package widgets.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import theme.Theme

@Composable
fun TextStickyHeader(textTitle:String,fontSize:Int=24,modifier: Modifier=Modifier) {
    Text(
        text = textTitle,
        color = Theme.colors.primaryTextColor,
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        modifier=modifier
    )
}