package platform

actual class PlatformConfiguration{
    actual val appname = "Rado"

    actual val platform: Platform
        get() = Platform.Desktop

    val appDataPath = "${System.getProperty("user.home")}\\AppData\\Local\\$appname"
}