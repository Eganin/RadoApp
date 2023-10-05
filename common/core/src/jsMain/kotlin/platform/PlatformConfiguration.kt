package platform

actual class PlatformConfiguration{
    actual val appname: String
        get() = "Rado"
    actual val platform: Platform
        get() = Platform.Web
}