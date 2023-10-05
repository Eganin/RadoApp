package platform

import android.content.Context

actual class PlatformConfiguration constructor(val androidContext: Context){
    actual val platform: Platform
        get() = Platform.Andorid

    actual val appname: String
        get() = "Rado"
}