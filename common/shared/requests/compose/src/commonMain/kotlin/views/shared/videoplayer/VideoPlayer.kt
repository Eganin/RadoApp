package views.shared.videoplayer

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import compose.icons.FeatherIcons
import compose.icons.feathericons.MinusCircle
import theme.Theme

@Composable
fun VideoPlayerCell(
    size: Dp,
    isExpanded: Boolean,
    url: String,
    isRemove: Boolean,
    eventHandler: () -> Unit,
    onRemove: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val sizeExpansion = 100.dp

    Box(
        modifier = if (isRemove) modifier.size(size).aspectRatio(1f)
            .clip(shape = RoundedCornerShape(size = 10.dp)) else modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            eventHandler.invoke()
        }.animateContentSize().size(if (isExpanded) size + sizeExpansion else size)
            .aspectRatio(1f).clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        VideoPlayerImpl(url = url)
        if (isRemove) {
            Box(contentAlignment = Alignment.TopStart, modifier = Modifier.clickable {
                onRemove.invoke()
            }.size(36.dp)) {
                Icon(
                    imageVector = FeatherIcons.MinusCircle,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Theme.colors.errorColor
                )
            }
        }
    }
}