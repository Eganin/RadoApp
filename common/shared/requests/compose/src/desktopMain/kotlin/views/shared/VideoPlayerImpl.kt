package views.shared

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery
import uk.co.caprica.vlcj.player.component.CallbackMediaPlayerComponent
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent
import views.isMacOS
import java.awt.Component

@Composable
actual fun VideoPlayerImpl(url: String) {
    val mediaPlayerComponent = remember { initializeMediaPlayerComponent() }
    val mediaPlayer = remember { mediaPlayerComponent.mediaPlayer() }

    val factory = remember { { mediaPlayerComponent } }

    LaunchedEffect(url) { mediaPlayer.media().play(url) }
    DisposableEffect(Unit) { onDispose(mediaPlayer::release) }
    SwingPanel(
        factory = factory,
        background = Color.Transparent,
        modifier = Modifier.fillMaxSize()
    )
}

private fun initializeMediaPlayerComponent(): Component {
    NativeDiscovery().discover()
    return if (isMacOS()) {
        CallbackMediaPlayerComponent()
    } else {
        EmbeddedMediaPlayerComponent()
    }
}

private fun Component.mediaPlayer() = when (this) {
    is CallbackMediaPlayerComponent -> mediaPlayer()
    is EmbeddedMediaPlayerComponent -> mediaPlayer()
    else -> error("mediaPlayer() can only be called on vlcj player components")
}