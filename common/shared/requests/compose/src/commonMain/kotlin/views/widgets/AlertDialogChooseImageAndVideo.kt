package views.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.icons.FeatherIcons
import compose.icons.feathericons.Plus
import ktor.BASE_URL
import org.company.rado.core.MainRes
import theme.Theme
import views.create.ImageCells
import views.shared.videoplayer.VideoPlayerCell

@Composable
internal fun AlertDialogChooseImageAndVideo(
    imageSize: Dp,
    resources: List<Triple<String, Boolean, ByteArray>>,
    resourceIsExpanded: Boolean,
    addResource: () -> Unit,
    imageOnClick: () -> Unit,
    videoOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = MainRes.string.image_fault_title,
        fontSize = 18.sp,
        color = Theme.colors.primaryTextColor,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyRow(modifier = modifier.fillMaxWidth()) {
        item {
            Box(modifier = Modifier.clickable {
                addResource.invoke()
            }.size(imageSize).background(Theme.colors.primaryAction)) {
                Icon(
                    modifier = Modifier.fillMaxSize(),
                    imageVector = FeatherIcons.Plus,
                    contentDescription = null
                )
            }
        }
        items(resources.filter { it.second }) {
            ImageCells(
                size = imageSize,
                isExpanded = resourceIsExpanded,
                imageLink = "$BASE_URL/resources/images/${it.first}",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                eventHandler = imageOnClick::invoke
            )
        }

        items(resources.filter { !it.second }) {
            VideoPlayerCell(
                size = imageSize,
                isExpanded = resourceIsExpanded,
                url = "$BASE_URL/resources/videos/${it.first}",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                eventHandler = videoOnClick::invoke
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}