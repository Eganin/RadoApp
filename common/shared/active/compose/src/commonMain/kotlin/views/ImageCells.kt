package views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter

@Composable
fun ImageCells(
    size: Dp,
    isExpanded: Boolean,
    imageLink: String,
    eventHandler: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sizeExpansion = 100.dp

    Box(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            eventHandler.invoke()
        }.animateContentSize().size(if (isExpanded) size + sizeExpansion else size)
            .aspectRatio(1f).clip(shape = RoundedCornerShape(size = 10.dp))
    ) {
        Image(
            painter = rememberImagePainter(url = imageLink),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}