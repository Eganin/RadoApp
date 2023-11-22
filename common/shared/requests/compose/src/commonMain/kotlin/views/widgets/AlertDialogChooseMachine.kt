package views.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ktor.BASE_URL
import org.company.rado.core.MainRes
import theme.Theme
import views.create.ImageMachineCells

@Composable
internal fun AlertDialogChooseMachine(
    imageSize: Dp,
    tractorIsExpanded: Boolean,
    trailerIsExpanded: Boolean,
    onClickTractor: () -> Unit,
    onClickTrailer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = MainRes.string.choose_machine_title,
        fontSize = 18.sp,
        color = Theme.colors.primaryTextColor,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        ImageMachineCells(
            imageSize = imageSize,
            title = MainRes.string.tractor_title,
            imageLink = "$BASE_URL/resources/images/tractor.jpg",
            isExpanded = tractorIsExpanded,
            eventHandler = {
                onClickTractor.invoke()
            }
        )

        ImageMachineCells(
            imageSize = imageSize,
            title = MainRes.string.trailer_title,
            imageLink = "$BASE_URL/resources/images/trailer.jpg",
            isExpanded = trailerIsExpanded,
            eventHandler = {
                onClickTrailer.invoke()
            }
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
}