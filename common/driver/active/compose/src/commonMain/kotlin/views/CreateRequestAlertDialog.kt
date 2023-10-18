package views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.Check
import io.github.skeptick.libres.compose.painterResource
import org.company.rado.core.MainRes
import theme.Theme

@Composable
fun CreateRequestAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit) {

    var expandedImageOne by remember { mutableStateOf(false) }
    var expandedImageTwo by remember { mutableStateOf(false) }

    val imageSize = (LocalDensity.current.density.dp * 60)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(32.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Row(modifier = Modifier.clickable { onExit.invoke() }) {
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Box(modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            expandedImageOne = !expandedImageOne
                        }
                        .animateContentSize()
                        .size(if (expandedImageOne) imageSize + 10.dp else imageSize)
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(size = 10.dp))) {
                        Image(
                            painter = painterResource(image = MainRes.image.tractor),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        if (expandedImageOne) {
                            Icon(
                                imageVector = FeatherIcons.Check,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                                    .background(color = Theme.colors.highlightColor.copy(alpha = 0.1f))
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            expandedImageTwo = !expandedImageTwo
                        }
                        .animateContentSize()
                        .size(if (expandedImageTwo) imageSize + 10.dp else imageSize)
                        .aspectRatio(1f)
                        .clip(shape = RoundedCornerShape(size = 10.dp))) {
                        Image(
                            painter = painterResource(image = MainRes.image.trailer),
                            contentDescription = "image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        if (expandedImageTwo) {
                            Icon(
                                imageVector = FeatherIcons.Check,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                                    .background(color = Theme.colors.highlightColor.copy(alpha = 0.1f))
                            )
                        }
                    }
                }
            }
        }
    }
}