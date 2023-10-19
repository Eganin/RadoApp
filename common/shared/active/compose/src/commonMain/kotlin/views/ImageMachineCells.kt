package views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.seiko.imageloader.rememberImagePainter
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check
import theme.Theme

@Composable
fun ImageMachineCells(
    imageSize: Dp,
    imageLink: String,
    title: String,
    isExpanded: Boolean,
    eventHandler: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sizeExpansion = 10.dp

    Column(modifier = modifier) {
        Box(
            modifier = Modifier.clickable(
                interactionSource = remember { MutableInteractionSource() }, indication = null
            ) {
                eventHandler.invoke()
            }.animateContentSize().size(if (isExpanded) imageSize + sizeExpansion else imageSize)
                .aspectRatio(1f).clip(shape = RoundedCornerShape(size = 10.dp))
        ) {
            Image(
                painter = rememberImagePainter(url = imageLink),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            if (isExpanded) {
                Icon(
                    imageVector = FeatherIcons.Check,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                        .background(color = Theme.colors.highlightColor.copy(alpha = 0.15f))
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = title,
            fontSize = 16.sp,
            color = Theme.colors.primaryTextColor,
            textAlign = TextAlign.Center
        )
    }
}