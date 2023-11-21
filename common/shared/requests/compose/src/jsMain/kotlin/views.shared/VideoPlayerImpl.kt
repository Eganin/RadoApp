package views.shared

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.jetbrains.compose.web.dom.Video
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLVideoElement

@Composable
actual fun VideoPlayerImpl(url: String) {
    //TODO realize for Web
val videoElement = document.createElement(localName = "video") as HTMLVideoElement
    videoElement.height=100
    videoElement.width=100
    videoElement.src=url
    videoElement.load()
    videoElement.play()
}