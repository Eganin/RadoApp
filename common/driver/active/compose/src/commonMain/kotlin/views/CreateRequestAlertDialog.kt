package views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.company.rado.core.MainRes
import platform.LocalPlatform
import platform.Platform
import theme.Theme

@Composable
fun CreateRequestAlertDialog(
    onDismiss: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isLargePlatform =
        LocalPlatform.current == Platform.Web || LocalPlatform.current == Platform.Desktop
    val imageSize =
        if (isLargePlatform) (LocalDensity.current.density.dp * 70) else (LocalDensity.current.density.dp * 25)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Theme.colors.primaryBackground)
        ) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
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

                Text(
                    text = MainRes.string.choose_machine_title,
                    fontSize = 18.sp,
                    color = Theme.colors.primaryTextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    ImageMachineCells(
                        imageSize = imageSize,
                        title = MainRes.string.tractor_title,
                        imageLink = "https://radoapp.serveo.net/resources/tractor.jpg"
                    )

                    ImageMachineCells(
                        imageSize = imageSize,
                        title = MainRes.string.trailer_title,
                        imageLink = "https://radoapp.serveo.net/resources/trailer.jpg"
                    )
                }
            }
        }
    }
}