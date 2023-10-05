package platform

actual class PlatformConfiguration{
    actual val appname : String
        get() = "Rado"

    actual val platform: Platform
        get() = Platform.Desktop

    val appDataPath = "${System.getProperty("user.home")}\\AppData\\Local\\$appname"
}