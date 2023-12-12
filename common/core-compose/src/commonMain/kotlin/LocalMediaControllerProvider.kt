import androidx.compose.runtime.staticCompositionLocalOf
import picker.LocalMediaController

val LocalMediaControllerProvider = staticCompositionLocalOf<LocalMediaController> {
    error("No get to media controller")
}