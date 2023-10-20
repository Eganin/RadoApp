package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter

@Composable
fun ImageCells(size: Dp,imageLink:String,modifier: Modifier=Modifier) {
    val sizeExpansion = 100.dp

    Box(
        modifier=modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null
    ) {

        }.size(size)){
        Image(
            painter = rememberImagePainter(url = imageLink),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}