import androidx.compose.runtime.staticCompositionLocalOf

val LocalPhoneControllerProvider = staticCompositionLocalOf<PhoneController> {
    error("No get to phone controller")
}