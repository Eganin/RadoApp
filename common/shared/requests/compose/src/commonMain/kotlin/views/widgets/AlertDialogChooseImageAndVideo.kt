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
import compose.icons.feathericons.Aperture
import compose.icons.feathericons.Plus
import compose.icons.feathericons.Video
import ktor.BASE_URL
import org.company.rado.core.MainRes
import platform.LocalPlatform
import platform.Platform
import theme.Theme
import views.create.ImageCells
import views.shared.videoplayer.VideoPlayerCell

@Composable
internal fun AlertDialogChooseImageAndVideo(
    imageSize: Dp,
    resources: List<Triple<String, Boolean, ByteArray>>,
    resourceIsExpanded: Boolean,
    addImageResource: () -> Unit,
    addVideoResource: () ->Unit,
    imageOnClick: () -> Unit,
    videoOnClick: () -> Unit,
    createdImages: List<String> = emptyList(),
    createdVideos: List<String> = emptyList(),
    isRemoveImageAndVideo: Boolean,
    removeVideoAction: (String) -> Unit = {},
    removeImageAction: (String) -> Unit = {},
    removeVideoFromResourceAction: (String) -> Unit = {},
    removeImageFromResourceAction: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop

    val isWeb = LocalPlatform.current == Platform.Web

    Text(
        text = MainRes.string.image_fault_title,
        fontSize = 18.sp,
        color = Theme.colors.primaryTextColor,
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyRow(modifier = modifier.fillMaxWidth()) {
        if (!isLargePlatform){
            item {
                Box(modifier = Modifier.clickable {
                    addImageResource.invoke()
                }.size(imageSize).background(Theme.colors.primaryAction)) {
                    Icon(
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                        imageVector = FeatherIcons.Aperture,
                        contentDescription = null
                    )
                }
            }

            item {
                Box(modifier = Modifier.clickable {
                    addVideoResource.invoke()
                }.padding(start=16.dp).size(imageSize).background(Theme.colors.primaryAction)) {
                    Icon(
                        modifier = Modifier.padding(16.dp).fillMaxSize(),
                        imageVector = FeatherIcons.Video,
                        contentDescription = null
                    )
                }
            }
        }else{
            item {
                Box(modifier = Modifier.clickable {
                    addImageResource.invoke()
                }.size(imageSize).background(Theme.colors.primaryAction)) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        imageVector = FeatherIcons.Plus,
                        contentDescription = null
                    )
                }
            }
        }

        items(createdImages) {
            ImageCells(
                size = imageSize,
                isExpanded = resourceIsExpanded,
                imageLink = it,
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                isRemove = isRemoveImageAndVideo,
                eventHandler = imageOnClick,
                onRemove = {
                    removeImageAction.invoke(it)
                }
            )
        }

        items(resources.filter { it.second }) {
            ImageCells(
                size = imageSize,
                isExpanded = resourceIsExpanded,
                imageLink = "$BASE_URL/resources/images/${it.first}",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                isRemove = isRemoveImageAndVideo,
                eventHandler = imageOnClick,
                onRemove = {
                    removeImageFromResourceAction.invoke(it.first)
                }
            )
        }

        if (!isWeb){
            items(createdVideos) {
                VideoPlayerCell(
                    size = imageSize,
                    isExpanded = resourceIsExpanded,
                    url = it,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    eventHandler = videoOnClick,
                    isRemove = isRemoveImageAndVideo,
                    onRemove = {
                        removeVideoAction.invoke(it)
                    }
                )
            }

            items(resources.filter { !it.second }) {
                VideoPlayerCell(
                    size = imageSize,
                    isExpanded = resourceIsExpanded,
                    url = "$BASE_URL/resources/videos/${it.first}",
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    eventHandler = videoOnClick,
                    isRemove = isRemoveImageAndVideo,
                    onRemove = {
                        removeVideoFromResourceAction.invoke(it.first)
                    }
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}