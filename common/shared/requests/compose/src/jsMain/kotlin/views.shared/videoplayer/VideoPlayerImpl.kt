package views.shared.videoplayer

import androidx.compose.runtime.Composable
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement

@Composable
actual fun VideoPlayerImpl(url: String) {
    //TODO realize for Web
    val videoElement = document.createElement(localName = "video") as HTMLVideoElement
    videoElement.height = 100
    videoElement.width = 100
    videoElement.src = url
    videoElement.load()
    videoElement.play()
}