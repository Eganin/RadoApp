package org.company.rado.android

import PlatformSDK
import android.app.Application
import platform.PlatformConfiguration

class RadoApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }
}

private fun RadoApp.initPlatformSDK(){
    PlatformSDK.init(
        platformConfiguration=PlatformConfiguration(androidContext=this)
    )
}